package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.Min;

public class UpdateProductQuantityRequest {

    @Min(value = 0)
    private int quantity;

    public UpdateProductQuantityRequest() {
    }

    public UpdateProductQuantityRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
