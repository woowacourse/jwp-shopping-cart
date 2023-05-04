package cart.request;

import javax.validation.constraints.NotNull;

public class ProductDto {
    @NotNull
    private Integer productId;

    public ProductDto() {
    }

    public ProductDto(final Integer productId) {
        this.productId = productId;
    }

    public Integer getProductId() {
        return productId;
    }
}
