package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Positive;

public class UpdateQuantityRequest {

    @Positive(message = "수량은 양수여야 합니다.")
    private int quantity;

    public UpdateQuantityRequest() {
    }

    public UpdateQuantityRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
