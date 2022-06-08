package woowacourse.shoppingcart.dto;

public class CartProductRequest {

    private long productId;

    public CartProductRequest() {
    }

    public CartProductRequest(long productId) {
        this.productId = productId;
    }

    public long getProductId() {
        return productId;
    }
}
