package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Positive;

public class PutCartItemRequest {

    @Positive(message = "수량은 양의 정수만 허용합니다.")
    private int quantity;

    private PutCartItemRequest() {
    }

    public PutCartItemRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
