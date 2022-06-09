package woowacourse.shoppingcart.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.repository.dao.CartItemDao;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("/init.sql")
class CartItemRepositoryTest {

    private CartItemRepository cartItemRepository;

    @Autowired
    public CartItemRepositoryTest(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.cartItemRepository = new CartItemRepository(new CartItemDao(namedParameterJdbcTemplate));
    }

    @DisplayName("사용자의 id 를 이용해서 저장된 모든 장바구니 속 물품들을 조회한다.")
    @Test
    void findCartItemsById() {
        // given
        Long customerId = 1L;

        // when
        List<CartItem> cartItems = cartItemRepository.selectCartItemsByCustomerId(customerId);

        // then
        assertAll(
                () -> assertThat(cartItems.size()).isEqualTo(3),
                () -> assertThat(cartItems.stream().map(CartItem::getProductId).collect(Collectors.toList()))
                        .containsExactly(1L, 2L, 3L),
                () -> assertThat(cartItems.stream().map(CartItem::getQuantity).collect(Collectors.toList()))
                        .containsExactly(5, 7, 9)
        );
    }

    @DisplayName("입력 받은 상품 id 들을 이용해서 장바구니에 새로운 물품을 추가한다.")
    @Test
    void addCartItems() {
        // given
        Long customerId = 1L;
        List<Long> productIds = List.of(3L, 4L);

        // when
        List<CartItem> cartItems = cartItemRepository.addCartItems(customerId, productIds);

        // then
        assertAll(
                () -> assertThat(cartItems.size()).isEqualTo(2),
                () -> assertThat(cartItems.get(0).getProductId()).isEqualTo(3L),
                () -> assertThat(cartItems.get(0).getQuantity()).isEqualTo(10),
                () -> assertThat(cartItems.get(1).getProductId()).isEqualTo(4L),
                () -> assertThat(cartItems.get(1).getQuantity()).isEqualTo(1)
        );
    }

    @DisplayName("기존의 장바구니 물품의 수량을 입력 받은 수량정보로 수정한다.")
    @Test
    void updateCartItem() {
        // given
        CartItem cartItem = CartItem.ofNullProductId(1L, 1L, 100);

        // when
        CartItem updatedCartItem = cartItemRepository.updateQuantity(cartItem);

        // then
        assertThat(updatedCartItem.getQuantity()).isEqualTo(100);
    }

    @DisplayName("장바구니 물품을 삭제한다")
    @Test
    void delete() {
        // given
        Long cartItemId = 3L;

        // when
        cartItemRepository.delete(cartItemId);

        // then
        assertThat(cartItemRepository.selectCartItemsByCustomerId(1L).size()).isEqualTo(2);
    }
}
