package cart.controller.dto.request;

import javax.validation.constraints.NotNull;

public class ProductIdRequest {

    @NotNull(message = "Id가 Null인 상품은 장바구니에 담을 수 없습니다.")
    private Long productId;

    public ProductIdRequest() {
    }

    public ProductIdRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

}
