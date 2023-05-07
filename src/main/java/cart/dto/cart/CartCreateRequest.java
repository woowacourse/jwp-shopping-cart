package cart.dto.cart;

public class CartCreateRequest {
    private Long productId;

    public CartCreateRequest() {
    }

    public CartCreateRequest(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
