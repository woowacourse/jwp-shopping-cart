package cart.controller.dto;

import javax.validation.constraints.NotNull;

public class CartProductRequest {

    @NotNull(message = "추가할 상품을 선택해주세요.")
    private final Long productId;

    public CartProductRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}