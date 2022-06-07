package woowacourse.shoppingcart.ui.dto.request;

import javax.validation.constraints.NotNull;

public class CartAddRequest {

    @NotNull
    private Long productId;

    public CartAddRequest() {
    }

    public CartAddRequest(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
