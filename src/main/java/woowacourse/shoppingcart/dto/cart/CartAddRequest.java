package woowacourse.shoppingcart.dto.cart;

import javax.validation.constraints.NotNull;

public class CartAddRequest {
    @NotNull
    private long productId;

    public CartAddRequest() {
    }

    public CartAddRequest(long productId) {
        this.productId = productId;
    }

    public long getProductId() {
        return productId;
    }
}
