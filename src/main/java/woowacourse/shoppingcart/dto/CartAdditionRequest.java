package woowacourse.shoppingcart.dto;

public class CartAdditionRequest {

    private Long productId;

    private CartAdditionRequest() {
    }

    public CartAdditionRequest(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
