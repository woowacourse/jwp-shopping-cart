package woowacourse.shoppingcart.dto.cart;

public class CartItemCreateRequest {

    private Long productId;
    private int count;

    private CartItemCreateRequest() {
    }

    public CartItemCreateRequest(Long productId, int count) {
        this.productId = productId;
        this.count = count;
    }

    public Long getProductId() {
        return productId;
    }

    public int getCount() {
        return count;
    }
}
