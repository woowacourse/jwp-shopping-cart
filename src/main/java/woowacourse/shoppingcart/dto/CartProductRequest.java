package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotNull;

public class CartProductRequest {

    @NotNull(groups = Request.id.class)
    private Long productId;

    private CartProductRequest() {
    }

    public CartProductRequest(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
