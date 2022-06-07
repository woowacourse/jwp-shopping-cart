package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Min;

public class CartQuantityUpdateRequest {

    @Min(value = 1, message = "장바구니 물품 개수를 1개 이상 입력해주세요.")
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
