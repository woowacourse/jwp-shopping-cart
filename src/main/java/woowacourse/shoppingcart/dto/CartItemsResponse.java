package woowacourse.shoppingcart.dto;

import java.util.List;

public class CartItemsResponse {

    private final List<CartItemResponse> cartItemItems;

    public CartItemsResponse(List<CartItemResponse> cartItemItems) {
        this.cartItemItems = cartItemItems;
    }

    public List<CartItemResponse> getCartItems() {
        return cartItemItems;
    }
}
