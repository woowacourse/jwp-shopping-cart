package cart.service.dto;

import javax.validation.constraints.NotNull;

public class CartRequest {

    @NotNull(message = "상품 id는 비어있을 수 없습니다.")
    private long productId;

    public CartRequest() {
    }

    public CartRequest(long productId) {
        this.productId = productId;
    }

    public long getProductId() {
        return productId;
    }
}
