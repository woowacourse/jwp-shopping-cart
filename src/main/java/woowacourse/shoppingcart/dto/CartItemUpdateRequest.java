package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Min;

public class CartItemUpdateRequest {

    @Min(value = 1, message = "잘못된 수량 형식입니다.")
    private int quantity;

    public CartItemUpdateRequest() {
    }

    public CartItemUpdateRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
