package woowacourse.shoppingcart.entity;

public class OrderDetailEntity {
    private final long orderId;
    private final long ProductId;
    private final int quantity;

    public OrderDetailEntity(long orderId, long productId, int quantity) {
        this.orderId = orderId;
        ProductId = productId;
        this.quantity = quantity;
    }

    public long getOrderId() {
        return orderId;
    }

    public long getProductId() {
        return ProductId;
    }

    public int getQuantity() {
        return quantity;
    }
}
