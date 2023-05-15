package cart.service.dto.cart;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class CartAddProductRequest {

    @NotBlank
    @Positive(message = "상품 id는 양수여야 합니다.")
    private Long productId;

    private CartAddProductRequest() {
    }

    public CartAddProductRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
