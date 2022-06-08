package woowacourse.shoppingcart.dto;

import java.util.List;

public class CartResponses {

    private List<CartResponse> cartItems;

    private CartResponses() {
    }

    public CartResponses(List<CartResponse> cartItems) {
        this.cartItems = cartItems;
    }

    public List<CartResponse> getCartItems() {
        return cartItems;
    }
}
