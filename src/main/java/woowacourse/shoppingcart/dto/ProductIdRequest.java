package woowacourse.shoppingcart.dto;

public class ProductIdRequest {

    private String productId;

    public ProductIdRequest() {}

    public ProductIdRequest(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }
}
