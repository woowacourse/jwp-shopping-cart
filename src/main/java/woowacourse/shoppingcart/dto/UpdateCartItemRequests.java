package woowacourse.shoppingcart.dto;

import java.util.List;

public class UpdateCartItemRequests {
    private List<UpdateCartItemRequest> cartItems;

    public UpdateCartItemRequests() {
    }

    public UpdateCartItemRequests(List<UpdateCartItemRequest> updateCartItemRequest) {
        this.cartItems = updateCartItemRequest;
    }

    public List<UpdateCartItemRequest> getCartItems() {
        return cartItems;
    }
}
