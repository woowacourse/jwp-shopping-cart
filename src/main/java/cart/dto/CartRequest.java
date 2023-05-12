package cart.dto;

public class CartRequest {
    private int productId;

    public CartRequest(){}

    public CartRequest(int productId) {
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }

}
