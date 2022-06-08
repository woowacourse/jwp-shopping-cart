package woowacourse.shoppingcart.dto;

import java.util.List;

public class DeleteCartItemRequests {

    private List<DeleteCartItemRequest> cartItemIds;

    public DeleteCartItemRequests() {
    }

    public DeleteCartItemRequests(List<DeleteCartItemRequest> deleteCartItemRequests) {
        this.cartItemIds = deleteCartItemRequests;
    }

    public List<DeleteCartItemRequest> getCartItemIds() {
        return cartItemIds;
    }
}
