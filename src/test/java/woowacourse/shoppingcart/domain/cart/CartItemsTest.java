package woowacourse.shoppingcart.domain.cart;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import woowacourse.shoppingcart.domain.Quantity;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.domain.product.vo.ThumbnailImage;
import woowacourse.shoppingcart.exception.DuplicateProductInCartException;

public class CartItemsTest {

    private final Product product1 = new Product(1L, "워치", 10_000, new ThumbnailImage("url", "alt"));
    private final Product product2 = new Product(2L, "맥북", 20_000, new ThumbnailImage("url", "alt"));

    @DisplayName("장바구니에 새로운 제품을 담는다.")
    @Test
    void add() {
        // given
        CartItem cartItem1 = new CartItem(1L, product1, new Quantity(10));
        CartItem cartItem2 = new CartItem(2L, product2, new Quantity(10));
        CartItems cartItems = new CartItems(1L, new ArrayList<>(List.of(cartItem1, cartItem2)));

        // when
        Product newProduct = new Product(3L, "에어팟", 5_000, new ThumbnailImage("url", "alt"));
        cartItems.add(new CartItem(3L, newProduct, new Quantity(10)));

        //then
        assertThat(cartItems.size()).isEqualTo(3);
    }

    @DisplayName("이미 있는 제품을 장바구니에 담을 경우 예외를 반환한다.")
    @Test
    void cantAddAlreadyExists() {
        // given
        CartItem cartItem1 = new CartItem(1L, product1, new Quantity(10));
        CartItem cartItem2 = new CartItem(2L, product2, new Quantity(10));
        CartItems cartItems = new CartItems(1L, new ArrayList<>(List.of(cartItem1, cartItem2)));

        // when & then
        assertThatThrownBy(() -> cartItems.add(new CartItem(3L, product1, new Quantity(10))))
            .isInstanceOf(DuplicateProductInCartException.class);
    }
}
