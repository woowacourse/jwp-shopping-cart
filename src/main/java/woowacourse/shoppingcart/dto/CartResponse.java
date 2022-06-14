package woowacourse.shoppingcart.dto;

import java.util.List;

public class CartResponse {
    private List<CartItemResponse> cartItems;

    private CartResponse() {
    }

    public CartResponse(List<CartItemResponse> cartResponses) {
        this.cartItems = cartResponses;
    }

    public List<CartItemResponse> getCartItems() {
        return cartItems;
    }
}
