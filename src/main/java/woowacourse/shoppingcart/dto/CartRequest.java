package woowacourse.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class CartRequest {

    private final long productId;

    @JsonCreator
    public CartRequest(long productId) {
        this.productId = productId;
    }

    public long getProductId() {
        return productId;
    }
}
