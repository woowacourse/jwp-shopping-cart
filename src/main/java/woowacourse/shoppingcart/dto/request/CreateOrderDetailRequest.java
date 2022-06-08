package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CreateOrderDetailRequest {

    @NotNull(message = "장바구니 ID를 입력해주세요😉")
    private final Long cartId;
    @Min(value = 1, message = "제품 수량은 1에서 99사이의 정수만 가능합니다😅")
    @Max(value = 99, message = "제품 수량은 1에서 99사이의 정수만 가능합니다😅")
    @NotNull(message = "상품 수량을 입력해주세요😉")
    private final int quantity;

    public CreateOrderDetailRequest(final Long cartId, final int quantity) {
        this.cartId = cartId;
        this.quantity = quantity;
    }

    public Long getCartId() {
        return cartId;
    }

    public int getQuantity() {
        return quantity;
    }
}
