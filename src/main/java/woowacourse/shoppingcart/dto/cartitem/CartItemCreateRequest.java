package woowacourse.shoppingcart.dto.cartitem;

public class CartItemCreateRequest {

    private Long productId;

    public CartItemCreateRequest() {
    }

    public CartItemCreateRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
