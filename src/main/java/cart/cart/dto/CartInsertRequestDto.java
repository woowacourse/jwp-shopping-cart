package cart.cart.dto;

import javax.validation.constraints.Positive;

public class CartInsertRequestDto {

    @Positive
    private int productId;

    public CartInsertRequestDto() {
    }

    public CartInsertRequestDto(final int productId) {
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }

}
