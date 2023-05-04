package cart.web.controller.dto.request;

public class ProductInCartAdditionRequest {
    private Long productId;

    public ProductInCartAdditionRequest() {
    }

    public ProductInCartAdditionRequest(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
