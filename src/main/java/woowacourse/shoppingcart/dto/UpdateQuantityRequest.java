package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Min;

public class UpdateQuantityRequest {

    @Min(1)
    private int quantity;

    private UpdateQuantityRequest() {
    }

    public UpdateQuantityRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
