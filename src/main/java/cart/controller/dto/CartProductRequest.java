package cart.controller.dto;

import javax.validation.constraints.NotNull;

public class CartProductRequest {

    @NotNull(message = "장바구니에 담을 상품이 있어야 합니다.")
    private Long productId;

    public CartProductRequest() {
    }

    public CartProductRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
