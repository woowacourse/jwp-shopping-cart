package woowacourse.shoppingcart.order.domain;

public class OrderDetail {

    private long orderId;
    private long productId;
    private long quantity;

    public OrderDetail() {
    }

    public OrderDetail(final long orderId, final long productId, final long quantity) {
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

    public long getQuantity() {
        return quantity;
    }
}
