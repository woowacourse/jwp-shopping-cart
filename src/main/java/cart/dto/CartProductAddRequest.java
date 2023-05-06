package cart.dto;

public class CartProductAddRequest {

    private Integer productId;

    public CartProductAddRequest() {
    }

    public CartProductAddRequest(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductId() {
        return productId;
    }
}
