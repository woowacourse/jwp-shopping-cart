package woowacourse.shoppingcart.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Cart;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartItemDaoTest {

    private final CartItemDao cartItemDao;

    public CartItemDaoTest(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        cartItemDao = new CartItemDao(namedParameterJdbcTemplate);
    }

    @DisplayName("카트에 아이템을 담으면, 담긴 카트 아이디를 반환한다. ")
    @Test
    void addCartItem() {
        // given
        final long customerId = 1L;
        final long productId = 1L;

        // when
        final Long cartId = cartItemDao.save(customerId, productId);

        // then
        assertThat(cartId).isEqualTo(3L);
    }

    @DisplayName("회원 아이디와 상품 아이디들로 카트 아이템 아이디들을 가져온다.")
    @Test
    void findIdsByCustomerIdAndProductIds() {
        final long customerId = 1L;
        final List<Long> productIds = List.of(1L, 2L);

        final List<Long> cartIds = cartItemDao.findIdsByCustomerIdAndProductIds(customerId, productIds);

        assertThat(cartIds).containsExactly(1L, 2L);
    }

    @DisplayName("회원 아이디를 넣으면, 해당 회원이 구매한 상품의 카트를 가져온다.")
    @Test
    void findByCustomerId() {
        // given
        final long customerId = 1L;

        // when
        final Cart cart = cartItemDao.findByCustomerId(customerId);

        // then
        assertThat(cart.getProducts().size()).isEqualTo(2);
    }

    @DisplayName("회원 아이디를 넣으면, 해당 회원이 구매한 상품의 아이디 목록을 가져온다.")
    @Test
    void findProductIdsByCustomerId() {
        // given
        final long customerId = 1L;

        // when
        final List<Long> productsIds = cartItemDao.findProductIdsByCustomerId(customerId);

        // then
        assertThat(productsIds).containsExactly(1L, 2L);
    }

    @DisplayName("Customer Id를 넣으면, 해당 장바구니 Id들을 가져온다.")
    @Test
    void findIdsByCustomerId() {
        // given
        final long customerId = 1L;

        // when
        final List<Long> cartIds = cartItemDao.findIdsByCustomerId(customerId);

        // then
        assertThat(cartIds).containsExactly(1L, 2L);
    }

    @DisplayName("Customer Id를 넣으면, 해당 장바구니 Id들을 가져온다.")
    @Test
    void deleteCartItem() {
        // given
        final long cartId = 1L;

        // when
        cartItemDao.deleteById(cartId);

        // then
        final long customerId = 1L;
        final List<Long> productIds = cartItemDao.findProductIdsByCustomerId(customerId);

        assertThat(productIds).containsExactly(2L);
    }

    @DisplayName("상품들을 장바구니에서 삭제한다.")
    @Test
    void deleteAll() {
        // given
        // when
        cartItemDao.deleteAll(List.of(1L, 2L));

        // then
        final long customerId = 1L;
        final List<Long> productIds = cartItemDao.findProductIdsByCustomerId(customerId);

        assertThat(productIds).hasSize(0);
    }
}
