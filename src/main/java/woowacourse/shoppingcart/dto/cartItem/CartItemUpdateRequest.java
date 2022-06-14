package woowacourse.shoppingcart.dto.cartItem;

import javax.validation.constraints.NotNull;

public class CartItemUpdateRequest {

    @NotNull
    private Integer quantity;

    public CartItemUpdateRequest() {
    }

    public CartItemUpdateRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
