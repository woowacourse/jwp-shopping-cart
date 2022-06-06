package woowacourse.shoppingcart.dto;

import java.util.List;

public class CartItemsResponse {
    private List<CartItemResponse> cartItems;

    public CartItemsResponse() {
    }

    public CartItemsResponse(List<CartItemResponse> cartItems) {
        this.cartItems = List.copyOf(cartItems);
    }

    public List<CartItemResponse> getCartItems() {
        return cartItems;
    }
}
