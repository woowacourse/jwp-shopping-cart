package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Min;

public class CartItemQuantityUpdateRequest {

    @Min(0)
    private final Integer quantity;

    public CartItemQuantityUpdateRequest() {
        this(null);
    }

    public CartItemQuantityUpdateRequest(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
