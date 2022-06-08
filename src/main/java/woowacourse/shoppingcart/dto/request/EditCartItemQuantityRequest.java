package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.NotNull;

public class EditCartItemQuantityRequest {

    @NotNull(message = "제품 수량을 입력해주세요😉")
    private int quantity;

    private EditCartItemQuantityRequest() {
    }

    public EditCartItemQuantityRequest(final int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
