package woowacourse.shoppingcart.dto;

import java.util.List;

public class DeleteCartItemRequests {

    private List<DeleteCartItemRequest> cartItems;

    public DeleteCartItemRequests() {
    }

    public DeleteCartItemRequests(List<DeleteCartItemRequest> deleteCartItemRequests) {
        this.cartItems = deleteCartItemRequests;
    }

    public List<DeleteCartItemRequest> getCartItems() {
        return cartItems;
    }
}
