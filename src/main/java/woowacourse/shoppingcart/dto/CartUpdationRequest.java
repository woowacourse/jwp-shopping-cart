package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Min;

public class CartUpdationRequest {

    @Min(value = 1, message = "1100:잘못된 형식입니다.")
    private int quantity;

    private CartUpdationRequest() {
    }

    public CartUpdationRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
