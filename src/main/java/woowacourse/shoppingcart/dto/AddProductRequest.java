package woowacourse.shoppingcart.dto;

public class AddProductRequest {

    private Long productId;

    private AddProductRequest() {
    }

    public AddProductRequest(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
