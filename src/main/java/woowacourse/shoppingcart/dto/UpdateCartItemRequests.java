package woowacourse.shoppingcart.dto;

import java.util.List;

public class UpdateCartItemRequests {

    private final List<UpdateCartItemRequest> cartItems;

    public UpdateCartItemRequests(List<UpdateCartItemRequest> cartItems) {
        this.cartItems = cartItems;
    }

    public List<UpdateCartItemRequest> getCartItems() {
        return cartItems;
    }
}
