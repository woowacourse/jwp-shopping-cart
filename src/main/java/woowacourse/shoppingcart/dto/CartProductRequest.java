package woowacourse.shoppingcart.dto;

public class CartProductRequest {

    private Long productId;

    private CartProductRequest() {
    }

    public CartProductRequest(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
