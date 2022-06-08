package woowacourse.shoppingcart.dto;

public class AddCartItemRequest {

    private Long productId;

    private AddCartItemRequest() {
    }

    public AddCartItemRequest(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
