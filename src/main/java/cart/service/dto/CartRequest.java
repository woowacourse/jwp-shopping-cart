package cart.service.dto;

public class CartRequest {

    private long productId;

    public CartRequest() {
    }

    public CartRequest(long productId) {
        this.productId = productId;
    }

    public long getProductId() {
        return productId;
    }
}
