package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class UpdateCartItemRequest {

    private Long cartItemId;

    @NotNull
    @Positive(message = "5002")
    private int quantity;

    public UpdateCartItemRequest() {
    }

    public UpdateCartItemRequest(Long cartItemId, int quantity) {
        this.cartItemId = cartItemId;
        this.quantity = quantity;
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public int getQuantity() {
        return quantity;
    }
}
