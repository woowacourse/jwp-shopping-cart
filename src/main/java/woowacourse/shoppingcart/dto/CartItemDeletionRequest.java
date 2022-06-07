package woowacourse.shoppingcart.dto;

import java.util.List;

public class CartItemDeletionRequest {
    private List<Long> cartItemIds;

    public CartItemDeletionRequest() {
    }

    public CartItemDeletionRequest(List<Long> cartItemIds) {
        this.cartItemIds = cartItemIds;
    }

    public List<Long> getCartItemIds() {
        return cartItemIds;
    }
}
