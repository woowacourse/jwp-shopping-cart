package cart.controller.dto;

import java.util.List;

public class CartResponse {

    private final Long cartId;
    private final List<ItemResponse> itemResponses;

    public CartResponse(Long cartId, List<ItemResponse> itemResponses) {
        this.cartId = cartId;
        this.itemResponses = itemResponses;
    }

    public Long getCartId() {
        return cartId;
    }

    public List<ItemResponse> getItemResponses() {
        return itemResponses;
    }
}
