package woowacourse.shoppingcart.dto;

import java.util.ArrayList;
import java.util.List;

public class DeleteCartItemIdsRequest {
    private final List<Long> cartItemIds;

    public DeleteCartItemIdsRequest(List<Long> cartItemIds) {
        this.cartItemIds = cartItemIds;
    }

    public DeleteCartItemIdsRequest(DeleteCartItemRequest deleteCartItemRequest) {
        this.cartItemIds = new ArrayList<>(deleteCartItemRequest.getProducts().values());
    }

    public List<Long> getCartItemIds() {
        return cartItemIds;
    }
}
