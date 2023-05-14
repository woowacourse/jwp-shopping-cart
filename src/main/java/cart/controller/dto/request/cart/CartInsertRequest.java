package cart.controller.dto.request.cart;

public class CartInsertRequest {

    private final Integer productId;

    private CartInsertRequest() {
        this(null);
    }

    public CartInsertRequest(final Integer productId) {
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }

}
