package cart.dto;

public class CartProductAddRequest {

    private int productId;

    public CartProductAddRequest() {
    }

    public CartProductAddRequest(int productId) {
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }
}
