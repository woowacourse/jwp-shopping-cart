package cart.dto;

public class CartProductRemoveRequest {

    private int productId;

    public CartProductRemoveRequest() {
    }

    public CartProductRemoveRequest(int productId) {
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }
}
