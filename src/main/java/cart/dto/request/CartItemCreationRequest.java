package cart.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CartItemCreationRequest {

    private final Long productId;

    @JsonCreator
    public CartItemCreationRequest(@JsonProperty(value = "productId") final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
