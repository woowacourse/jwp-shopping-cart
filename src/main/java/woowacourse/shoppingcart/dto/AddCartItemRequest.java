package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotNull;

public class AddCartItemRequest {

    @NotNull
    private Long productId;

    private AddCartItemRequest() {
    }

    public AddCartItemRequest(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
