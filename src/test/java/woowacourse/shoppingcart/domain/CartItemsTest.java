package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Nested
@DisplayName("CartItems 클래스의")
class CartItemsTest {

    private final CartItem 피자 = new CartItem(1L, 1L, "피자", 20_000, "http://example.com/pizza.jpg", 2);
    private final CartItem 햄버거 = new CartItem(2L, 2L, "햄버거", 2_000, "http://example.com/burger.jpg", 3);
    private final CartItem 콜라 = new CartItem(3L, 3L, "콜라", 200, "http://example.com/coke.jpg", 5);

    private CartItems initializeCartItems() {
        ArrayList<CartItem> cartItems = new ArrayList<>();
        cartItems.add(피자);
        cartItems.add(햄버거);
        return CartItems.of(cartItems);
    }

    @Nested
    @DisplayName("add 메서드는")
    class add {

        @Test
        @DisplayName("상품을 추가한다.")
        void success() {
            // given
            CartItems cartItems = initializeCartItems();

            // when & then
            assertThatNoException().isThrownBy(() -> cartItems.add(콜라));
        }

        @Test
        @DisplayName("동일한 상품은 추가하면 예외를 던진다.")
        void cartItem_alreadyExist() {
            // given
            CartItems cartItems = initializeCartItems();

            // when & then
            assertThatThrownBy(() -> cartItems.add(피자))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("이미 장바구니에 상품이 존재합니다.");
        }

    }

    @Nested
    @DisplayName("findById 메서드는")
    class findById {

        @Test
        @DisplayName("장바구니 아이템 id에 해당하는 상품을 반환한다.")
        void success() {
            // given
            CartItems cartItems = initializeCartItems();

            // when
            CartItem cartItem = cartItems.findById(피자.getId());

            // then
            assertAll(
                    () -> assertThat(cartItem.getProductId()).isEqualTo(피자.getProductId()),
                    () -> assertThat(cartItem.getName()).isEqualTo(피자.getName()),
                    () -> assertThat(cartItem.getPrice()).isEqualTo(피자.getPrice()),
                    () -> assertThat(cartItem.getImageUrl()).isEqualTo(피자.getImageUrl()),
                    () -> assertThat(cartItem.getQuantity()).isEqualTo(피자.getQuantity())
            );
        }

        @Test
        @DisplayName("장바구니 아이템 id에 해당하는 상품이 존재하지 않는 경우 예외를 던진다.")
        void item_notExist() {
            // given
            CartItems cartItems = initializeCartItems();
            Long id = 콜라.getId();

            // when & then
            assertThatThrownBy(() -> cartItems.findById(id))
                    .isInstanceOf(InvalidCartItemException.class);
        }
    }
}
