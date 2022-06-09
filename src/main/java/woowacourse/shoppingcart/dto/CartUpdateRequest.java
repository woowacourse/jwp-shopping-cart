package woowacourse.shoppingcart.dto;

import javax.validation.constraints.PositiveOrZero;

public class CartUpdateRequest {
    @PositiveOrZero
    private int quantity;

    public CartUpdateRequest() {
    }

    public CartUpdateRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
