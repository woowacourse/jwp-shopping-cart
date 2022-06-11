package woowacourse.shoppingcart.entity;

public class CartItemEntity {

    private final Long customerId;
    private final Long productId;

    public CartItemEntity(Long customerId, Long productId) {
        this.customerId = customerId;
        this.productId = productId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getProductId() {
        return productId;
    }
}
