package woowacourse.shoppingcart.ui.dto;

import javax.validation.constraints.Min;

public class CartItemQuantityRequest {

    @Min(0)
    private final Integer quantity;

    private CartItemQuantityRequest() {
        this(null);
    }

    public CartItemQuantityRequest(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
