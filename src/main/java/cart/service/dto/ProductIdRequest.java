package cart.service.dto;

public class ProductIdRequest {

    private final Long productId;

    private ProductIdRequest(final Long productId) {
        this.productId = productId;
    }

    private ProductIdRequest() {
        this(null);
    }

    public Long getProductId() {
        return productId;
    }
}
