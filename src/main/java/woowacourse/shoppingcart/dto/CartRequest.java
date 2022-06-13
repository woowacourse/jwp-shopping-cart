package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotNull;

public class CartRequest {

    @NotNull(message = "product id를 입력하세요.")
    private Long productId;

    private CartRequest() {
    }

    public CartRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
