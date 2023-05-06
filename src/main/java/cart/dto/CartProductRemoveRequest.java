package cart.dto;

public class CartProductRemoveRequest {

    private Integer productId;

    public CartProductRemoveRequest() {
    }

    public CartProductRemoveRequest(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductId() {
        return productId;
    }
}
