package woowacourse.shoppingcart.cartitem.dto;

import java.util.List;

public class DeleteCartItemRequest {
    private List<Long> cartItemIds;

    public DeleteCartItemRequest() {
    }

    public DeleteCartItemRequest(List<Long> cartItemIds) {
        this.cartItemIds = cartItemIds;
    }

    public List<Long> getCartItemIds() {
        return cartItemIds;
    }
}
