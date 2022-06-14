package woowacourse.shoppingcart.entity;

public class CartItemEntity {
    private final long id;
    private final long customerId;
    private final long productId;
    private final int quantity;

    public CartItemEntity(long id, long customerId, long productId, int quantity) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public long getProductId() {
        return productId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public int getQuantity() {
        return quantity;
    }
}
