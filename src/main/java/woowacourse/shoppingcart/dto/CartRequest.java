package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class CartRequest {
    @Min(value = 1, message = "개수는 1이상이어야 합니다. : ${validatedValue}")
    @Max(value = 99, message = "개수는 99이하여야 합니다. : ${validatedValue}")
    private int quantity;

    private CartRequest() {
    }

    public CartRequest(final int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
