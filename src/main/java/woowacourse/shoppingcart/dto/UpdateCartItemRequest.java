package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Min;

public class UpdateCartItemRequest {

    @Min(0)
    private int quantity;

    private UpdateCartItemRequest() {
    }

    public UpdateCartItemRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
