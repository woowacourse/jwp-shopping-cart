package woowacourse.shoppingcart.dto;

public class AddCartRequest {

    private Long productId;

    public AddCartRequest() {
    }

    public AddCartRequest(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
