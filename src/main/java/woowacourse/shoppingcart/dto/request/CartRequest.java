package woowacourse.shoppingcart.dto.request;

public class CartRequest {

    private Long productId;

    public CartRequest() {
    }

    public CartRequest(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
