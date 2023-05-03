package cart.dto;

public class CartRequest {

    private Long productId;

    private CartRequest() {
    }

    public CartRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
