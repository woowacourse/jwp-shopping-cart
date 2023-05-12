package cart.dto.request;

import javax.validation.constraints.NotNull;

public class ProductRequestDto {
    @NotNull
    private int productId;

    public ProductRequestDto() {
    }

    public ProductRequestDto(final int productId) {
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }
}
