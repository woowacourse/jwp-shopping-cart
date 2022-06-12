package woowacourse.shoppingcart.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dao.dto.SaveCartDto;
import woowacourse.shoppingcart.domain.Cart;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartDaoTest {

    private final CartDao cartDao;
    private final Long memberId = 1L;

    public CartDaoTest(JdbcTemplate jdbcTemplate) {
        cartDao = new CartDao(jdbcTemplate);
    }

    @DisplayName("카트에 아이템을 담으면, 담긴 카트 아이디를 반환한다.")
    @Test
    void save() {
        Long cartItemId = cartDao.save(new SaveCartDto(memberId, 1L));

        assertThat(cartItemId).isEqualTo(6L);
    }

    @DisplayName("고객 아이디를 넣으면, 해당 고객이 장바구니 목록을 가져온다.")
    @Test
    void findCartByMemberId() {
        List<Cart> carts = cartDao.findCartByMemberId(memberId);
        List<Long> productIds = carts.stream()
                .map(Cart::getProductId)
                .collect(Collectors.toList());

        assertThat(productIds).containsExactly(1L, 2L);
    }

    @DisplayName("회원 ID와 상품 ID에 매칭되는 장바구니 정보를 가져온다.")
    @Test
    void findCartByMemberIdAndProductId() {
        long memberId = 1L;
        long productId = 1L;
        Optional<Cart> cart = cartDao.findCartByMemberIdAndProductId(memberId, productId);

        assertThat(cart).isNotEmpty();
    }

    @DisplayName("회원 ID와 상품 ID에 매칭되는 장바구니 정보가 없으면 비어있는 Optional을 반환한다.")
    @Test
    void failToFindCartByMemberIdAndProductId() {
        long memberId = 1L;
        long productId = 100L;
        Optional<Cart> cart = cartDao.findCartByMemberIdAndProductId(memberId, productId);

        assertThat(cart).isEmpty();
    }

    @DisplayName("장바구니 ID에 매칭되는 장바구니 정보를 반환한다.")
    @Test
    void findCartById() {
        long cartId = 1L;
        Optional<Cart> cart = cartDao.findCartById(cartId);

        assertThat(cart).isNotEmpty();
    }

    @DisplayName("장바구니 ID에 매칭되는 장바구니 정보가 없으면 비어있는 Optional을 반환한다.")
    @Test
    void failToFindCartById() {
        long cartId = 100L;
        Optional<Cart> cart = cartDao.findCartById(cartId);

        assertThat(cart).isEmpty();
    }

    @DisplayName("상품 수량을 업데이트 한다.")
    @Test
    void updateQuantity() {
        cartDao.updateQuantity(1L, 10);

        List<Cart> carts = cartDao.findCartByMemberId(memberId);
        Optional<Cart> updatedCart = carts.stream()
                .filter(v -> v.getId() == 1L)
                .findAny();

        assertThat(updatedCart.get().getQuantity()).isEqualTo(10);
    }

    @DisplayName("CartItem Id를 넣으면, 해당 아이템을 장바구니에서 삭제한다.")
    @Test
    void deleteById() {
        cartDao.deleteById(1L);

        List<Cart> cartItems = cartDao.findCartByMemberId(memberId);
        List<Long> productIds = cartItems.stream()
                .map(Cart::getProductId)
                .collect(Collectors.toList());

        assertThat(productIds).doesNotContain(1L);
    }
}
