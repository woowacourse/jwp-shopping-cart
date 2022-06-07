package woowacourse.shoppingcart.ui.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CartCreateRequest {
    @NotNull
    @Positive
    private Long productId;

    public CartCreateRequest() {
    }

    public CartCreateRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
