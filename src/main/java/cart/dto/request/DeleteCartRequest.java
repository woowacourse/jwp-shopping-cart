package cart.dto.request;

import javax.validation.constraints.NotNull;

public class DeleteCartRequest {

    @NotNull(message = "상품 id가 입력되지 않았습니다.")
    private Long productId;

    public DeleteCartRequest() {
    }

    public DeleteCartRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId(){
        return productId;
    }
}
