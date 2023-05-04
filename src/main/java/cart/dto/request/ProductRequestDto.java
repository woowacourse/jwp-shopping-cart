package cart.dto.request;

import javax.validation.constraints.NotNull;

public class ProductRequestDto {
    @NotNull
    private Integer productId;

    public ProductRequestDto() {
    }

    public ProductRequestDto(final Integer productId) {
        this.productId = productId;
    }

    public Integer getProductId() {
        return productId;
    }
}
