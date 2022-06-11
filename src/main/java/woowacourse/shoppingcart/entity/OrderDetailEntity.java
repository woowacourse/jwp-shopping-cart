package woowacourse.shoppingcart.entity;

public class OrderDetailEntity {

    private final long orderId;
    private final long productId;
    private final int quantity;

    public OrderDetailEntity(long orderId, long productId, int quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public long getOrderId() {
        return orderId;
    }

    public long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
