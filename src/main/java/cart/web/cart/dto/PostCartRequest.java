package cart.web.cart.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class PostCartRequest {

    @NotNull(message = "유효한 ID를 입력해주세요")
    @Positive
    private Long productId;

    private PostCartRequest() {
    }

    public PostCartRequest(final Long productId) {
        this.productId = productId;
    }

    public long getProductId() {
        return productId;
    }
}
