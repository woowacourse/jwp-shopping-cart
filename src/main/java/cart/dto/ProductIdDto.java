package cart.dto;

public class ProductIdDto {
    private Long productId;

    public ProductIdDto() {
    }

    public ProductIdDto(Long productId) {
        this.productId = productId;
    }

    public long getProductId() {
        return productId;
    }
}
