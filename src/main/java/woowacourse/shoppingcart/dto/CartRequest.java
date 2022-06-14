package woowacourse.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotNull;

public class CartRequest {

    @NotNull
    private final long productId;

    @JsonCreator
    public CartRequest(long productId) {
        this.productId = productId;
    }

    public long getProductId() {
        return productId;
    }
}
