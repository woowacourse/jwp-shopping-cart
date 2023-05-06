package cart.service.dto;

public class CartDto {

    private long productId;

    public CartDto(long productId) {
        this.productId = productId;
    }

    public long getProductId() {
        return productId;
    }
}
