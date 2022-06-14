package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Min;

public class QuantityRequest {
    @Min(value = 0, message = "상품 개수는 0개 이상이어야 합니다.")
    private int quantity;

    private QuantityRequest() {
    }

    public QuantityRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
