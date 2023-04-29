package cart.dto.request.cart;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CartDeleteRequest {
    @NotNull(message = "장바구니의 ID가 비어있습니다.")
    @Positive(message = "장바구니의 ID는 0보다 커야 합니다.")
    @Max(
            value = Long.MAX_VALUE,
            message = "장바구니의 ID는 {value}보다 작아야 합니다."
    )
    private Long cartId;

    public CartDeleteRequest() {
    }

    public CartDeleteRequest(Long cartId) {
        this.cartId = cartId;
    }

    public Long getCartId() {
        return cartId;
    }
}
