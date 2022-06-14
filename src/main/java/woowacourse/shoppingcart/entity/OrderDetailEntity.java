package woowacourse.shoppingcart.entity;

public class OrderDetailEntity {

    private final Long orderId;
    private final Long productId;
    private final int quantity;


    public OrderDetailEntity(final Long orderId, final Long productId, final int quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
