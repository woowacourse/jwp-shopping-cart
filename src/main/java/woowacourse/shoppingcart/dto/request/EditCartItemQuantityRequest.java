package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class EditCartItemQuantityRequest {

    @Min(value = 1, message = "제품 수량은 1에서 99사이의 정수만 가능합니다😅")
    @Max(value = 99, message = "제품 수량은 1에서 99사이의 정수만 가능합니다😅")
    @NotNull(message = "제품 수량을 입력해주세요😉")
    private int quantity;

    public EditCartItemQuantityRequest() {
    }

    public EditCartItemQuantityRequest(final int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
