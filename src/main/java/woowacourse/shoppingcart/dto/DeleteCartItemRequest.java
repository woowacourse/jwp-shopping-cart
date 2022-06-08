package woowacourse.shoppingcart.dto;

import java.util.Map;

public class DeleteCartItemRequest {

    private Map<String, Long> cartItems;

    public DeleteCartItemRequest() {
    }

    public DeleteCartItemRequest(Map<String, Long> cartItems) {
        this.cartItems = cartItems;
    }

    public Map<String, Long> getCartItems() {
        return cartItems;
    }
}
