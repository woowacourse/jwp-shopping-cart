package cart.controller.dto;

public class CartItemRequest {

    private Integer productId;

    public CartItemRequest() {
    }

    public CartItemRequest(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductId() {
        return productId;
    }
}
