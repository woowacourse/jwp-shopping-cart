package cart.dto;

import javax.validation.constraints.NotNull;

public final class UserCartRequest {
    @NotNull(message = "상품 id를 입력해야 합니다.")
    private Long productId;

    public UserCartRequest() {
    }

    public UserCartRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
