package cart.dto.cart;

public class CartRequest {

    private Long productId;

    public CartRequest() {
    }

    public CartRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
