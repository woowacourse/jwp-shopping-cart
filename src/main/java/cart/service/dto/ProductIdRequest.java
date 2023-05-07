package cart.service.dto;

public class ProductIdRequest {

    private final Long productId;

    private ProductIdRequest() {
        this(null);
    }

    private ProductIdRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
