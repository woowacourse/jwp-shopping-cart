package cart.dto.cart;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class AddProductRequest {

    @NotNull(message = "상품 id는 필수 입력값입니다.")
    @Positive(message = "상품 id는 0보다 커야 합니다. 입력값 : ${validatedValue}")
    private Long productId;

    private AddProductRequest() {
    }

    public AddProductRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
