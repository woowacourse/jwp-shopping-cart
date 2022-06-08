package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CreateCartItemRequest {

    @NotNull(message = "상품 ID를 입력해주세요😉")
    private Long id;
    @Min(value = 1, message = "상품 수량은 1에서 99사이의 정수만 가능합니다😅")
    @Max(value = 99, message = "상품 수량은 1에서 99사이의 정수만 가능합니다😅")
    @NotNull(message = "상품 수량을 입력해주세요😉")
    private int quantity;

    public CreateCartItemRequest() {
    }

    public CreateCartItemRequest(final Long id, final int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }
}
