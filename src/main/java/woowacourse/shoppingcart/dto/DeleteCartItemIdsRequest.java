package woowacourse.shoppingcart.dto;

import java.util.List;

public class DeleteCartItemIdsRequest {
    private final List<DeleteCartItemRequest> cartItemIds;

    public DeleteCartItemIdsRequest(List<DeleteCartItemRequest> cartItemIds) {
        this.cartItemIds = cartItemIds;
    }

    public DeleteCartItemIdsRequest(DeleteCartItemRequest deleteCartItemRequest) {
        this.cartItemIds = List.of(deleteCartItemRequest);
    }

    public List<DeleteCartItemRequest> getCartItemIds() {
        return cartItemIds;
    }
}
