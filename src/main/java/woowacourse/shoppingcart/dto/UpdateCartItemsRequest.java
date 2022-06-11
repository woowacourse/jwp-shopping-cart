package woowacourse.shoppingcart.dto;

import java.util.List;

public class UpdateCartItemsRequest {

    private List<UpdateCartItemRequest> cartItems;

    private UpdateCartItemsRequest() {
    }

    public UpdateCartItemsRequest(List<UpdateCartItemRequest> cartItems) {
        this.cartItems = cartItems;
    }

    public List<UpdateCartItemRequest> getCartItems() {
        return cartItems;
    }
}
