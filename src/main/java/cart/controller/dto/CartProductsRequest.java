package cart.controller.dto;

import javax.validation.constraints.NotNull;

public class CartProductsRequest {

    @NotNull(message = "카트에 추가할 상품아이디(productId)가 있어야 합니다.")
    private Long productId;

    public CartProductsRequest() {
    }

    public CartProductsRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
