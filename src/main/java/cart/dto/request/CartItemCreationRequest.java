package cart.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class CartItemCreationRequest {

    @NotNull
    private final Long productId;

    @JsonCreator
    public CartItemCreationRequest(@JsonProperty(value = "productId") final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
