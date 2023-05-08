package cart.dto;

public class CartAddRequest {

    private long productId;

    private CartAddRequest() {
    }

    public CartAddRequest(final long productId) {
        this.productId = productId;
    }

    public long getProductId() {
        return productId;
    }
}
