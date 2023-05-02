package cart.product.dto;

public class ProductUpdateResponseDto {
    private final int updatedRowCount;

    public ProductUpdateResponseDto(final int updatedRowCount) {
        this.updatedRowCount = updatedRowCount;
    }

    public int getUpdatedRowCount() {
        return updatedRowCount;
    }
}
