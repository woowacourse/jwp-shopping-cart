package woowacourse.shoppingcart.service.dto;

public class CartCreateServiceRequest {
    private long productId;

    public CartCreateServiceRequest(final long productId) {
        this.productId = productId;
    }

    public long getProductId() {
        return productId;
    }
}
