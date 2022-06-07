package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Min;

public class CartQuantityUpdateRequest {

    @Min(value = 1)
    private int quantity;

    private CartQuantityUpdateRequest() {

    }

    public CartQuantityUpdateRequest(final int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
