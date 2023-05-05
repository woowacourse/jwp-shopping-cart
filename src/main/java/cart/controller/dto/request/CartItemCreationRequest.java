package cart.controller.dto.request;

public class CartItemCreationRequest {
    private Long productId;

    public CartItemCreationRequest() {

    }

    public CartItemCreationRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
