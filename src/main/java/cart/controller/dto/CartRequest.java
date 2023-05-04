package cart.controller.dto;

import javax.validation.constraints.NotNull;

public class CartRequest {

    @NotNull
    private final Long userId;
    @NotNull
    private final Long itemId;

    public CartRequest(Long userId, Long itemId) {
        this.userId = userId;
        this.itemId = itemId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getItemId() {
        return itemId;
    }
}
