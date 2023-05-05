package cart.controller.dto.request;

public class CartItemDeleteRequest {

    private Long productId;

    public CartItemDeleteRequest() {
    }

    public CartItemDeleteRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
