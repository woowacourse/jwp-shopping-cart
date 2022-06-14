package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Min;

public class CartQuantityRequest {

    @Min(value = 0, groups = {Request.allProperties.class})
    private int quantity;

    public CartQuantityRequest() {
    }

    public CartQuantityRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
