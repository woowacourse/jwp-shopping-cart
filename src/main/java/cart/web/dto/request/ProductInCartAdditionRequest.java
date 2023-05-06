package cart.web.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.NotNull;

public class ProductInCartAdditionRequest {
    @NotNull
    private final Long productId;

    @JsonCreator
    public ProductInCartAdditionRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
