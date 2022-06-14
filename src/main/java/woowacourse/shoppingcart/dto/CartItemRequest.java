package woowacourse.shoppingcart.dto;

public class CartItemRequest {

    private long productId;

    public CartItemRequest() {
    }

    public CartItemRequest(long productId) {
        this.productId = productId;
    }

    public long getProductId() {
        return productId;
    }
}
