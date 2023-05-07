package cart.controller.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class DeleteCartItemRequest {

    @NotNull
    @Positive
    private Long cartId;

    private DeleteCartItemRequest() {
    }

    public DeleteCartItemRequest(final Long cartId) {
        this.cartId = cartId;
    }

    public Long getProductId() {
        return cartId;
    }
}
