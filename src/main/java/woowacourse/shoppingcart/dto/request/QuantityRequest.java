package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.Min;

public class QuantityRequest {
    @Min(value = 1, message = "수량은 1 이상이어야 합니다.")
    private int quantity;

    public QuantityRequest() {
    }

    public QuantityRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
