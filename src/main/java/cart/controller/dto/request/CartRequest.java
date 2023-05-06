package cart.controller.dto.request;

import javax.validation.constraints.NotNull;

public class CartRequest {

    @NotNull
    private final Long itemId;

    public CartRequest(Long itemId) {
        this.itemId = itemId;
    }

    public Long getItemId() {
        return itemId;
    }
}
