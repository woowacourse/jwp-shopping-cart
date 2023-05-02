package cart.cart.dto;

public class CartInsertRequestDto {

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
