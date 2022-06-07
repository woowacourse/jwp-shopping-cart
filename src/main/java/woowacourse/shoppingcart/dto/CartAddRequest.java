package woowacourse.shoppingcart.dto;

public class CartAddRequest {
    private Long productId;

    public CartAddRequest() {
    }

    public CartAddRequest(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
