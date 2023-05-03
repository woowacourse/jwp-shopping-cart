package cart.web.cart.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class PostCartRequest {

    @NotNull(message = "유효한 product id를 입력해주세요")
    @Positive(message = "유효한 product id를 입력해주세요")
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
