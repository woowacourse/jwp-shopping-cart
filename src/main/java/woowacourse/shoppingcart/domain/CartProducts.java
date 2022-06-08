package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.dto.CartProductResponse;

import java.util.List;

public class CartProducts {

    private final List<CartProductResponse> cartItems;

    public CartProducts(List<CartProductResponse> cartItems) {
        this.cartItems = cartItems;
    }

    public List<CartProductResponse> getCartItems() {
        return cartItems;
    }
}
