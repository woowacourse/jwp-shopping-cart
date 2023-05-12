package cart.dto.request;

import javax.validation.constraints.NotNull;

public class CreateCartRequest {

    @NotNull(message = "상품 id가 입력되지 않았습니다.")
    private Long productId;

    public CreateCartRequest() {
    }

    public CreateCartRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId(){
        return productId;
    }
}
