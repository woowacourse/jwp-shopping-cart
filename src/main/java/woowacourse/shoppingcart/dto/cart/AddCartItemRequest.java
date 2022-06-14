package woowacourse.shoppingcart.dto.cart;

import javax.validation.constraints.NotNull;

public class AddCartItemRequest {

    @NotNull
    private Long productId;

    public AddCartItemRequest() {
    }

    public AddCartItemRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
