package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dto.response.CartItemResponse;
import woowacourse.shoppingcart.dto.response.CartResponse;
import woowacourse.shoppingcart.exception.DuplicateCartItemByProduct;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@SuppressWarnings("NonAsciiChracters")
@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql",
    "classpath:data.sql", "classpath:cartservice_data.sql"})
class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Test
    void findCartByCustomerId메서드는_고객id로_장바구니를_찾는다() {
        final Long customerId = 1L;

        CartResponse cartResponse = cartService.findCartByCustomerId(customerId);

        assertThat(cartResponse.getCartItemResponses());
    }

    @DisplayName("addCart 메서드는 장바구니에 물품을 추가한다.")
    @Nested
    class AddCart {

        @Test
        void 장바구니에_정상적인_물품을_추가할_경우_성공() {
            final Long customerId = 5L;
            final Long productId = 5L;

            CartItemResponse cartItemResponse = cartService.addCart(productId, customerId);

            assertAll(() -> {
                assertThat(cartItemResponse.getId()).isEqualTo(4L);
                assertThat(cartItemResponse.getQuantity()).isEqualTo(1);
                assertThat(cartItemResponse.getProduct())
                    .extracting("id", "name", "price", "imageUrl")
                    .containsExactly(5L, "[승팡] 페루산 냉동 애플망고(2cmx2cm다이스) 1kg", 5_090,
                        "https://cdn-mart.baemin.com/sellergoods/main/2bb8dfcb-e4b0-48ff-a028-05aa9d9c2f19.jpg");
            });
        }

        @Test
        void 장바구니에_이미_등록된_물품인_경우_예외발생() {
            final Long customerId = 1L;
            final Long productId = 5L;

            cartService.addCart(customerId, productId);

            assertThatThrownBy(() -> cartService.addCart(customerId, productId))
                .isInstanceOf(DuplicateCartItemByProduct.class);
        }
    }

    @DisplayName("deleteCartItem메서드는 장바구니에 추가된 물품을 삭제한다.")
    @Nested
    class DeleteCartItem {

        @Test
        void 존재하는_장바구니_물품을_삭제할_경우_성공() {
            final Long customerId = 1L;
            final Long productId = 1L;

            assertThatCode(() -> cartService.deleteCartItem(customerId, productId))
                .doesNotThrowAnyException();
        }

        @Test
        void 존재하지_않는_물품을_삭제할_경우_예외발생() {
            final Long customerId = 1L;
            final Long productId = 3L;

            assertThatThrownBy(() -> cartService.deleteCartItem(customerId, productId))
                .isInstanceOf(NotInCustomerCartItemException.class);
        }
    }

    @Test
    void updateCartItemQuantity메서드는_장바구니_물품_개수를_변경한다() {
        final Long customerId = 1L;
        final Long cartId = 1L;
        final int quantity = 10;

        CartItemResponse cartItemResponse = cartService.updateCartItemQuantity(customerId, cartId,
            quantity);

        assertThat(cartItemResponse.getQuantity()).isEqualTo(10);
    }

    @Test
    void deleteCart메서드는_장바구니에_포함된_물품전체를_삭제한다() {
        final Long customerId = 1L;

        cartService.deleteCart(customerId);

        assertThat(cartService.findCartByCustomerId(customerId).getCartItemResponses()).hasSize(0);
    }
}
