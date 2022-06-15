package woowacourse.shoppingcart.domain.order;

public class Order {

    private final long orderId;
    private final long productId;
    private final int quantity;

    public Order(Long orderId, Long productId, int quantity) {
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
