package woowacourse.shoppingcart.infra.dao.entity;

public class OrderEntity {
    private final long orderId;
    private final long orderDetailId;
    private final Long cartId;
    private final long customerId;
    private final ProductEntity productEntity;
    private final int quantity;

    public OrderEntity(final long orderId, final long orderDetailId, final Long cartId, final long customerId,
                       final ProductEntity productEntity,
                       final int quantity) {
        this.orderId = orderId;
        this.orderDetailId = orderDetailId;
        this.cartId = cartId;
        this.customerId = customerId;
        this.productEntity = productEntity;
        this.quantity = quantity;
    }

    public OrderEntity(final long orderId, final long orderDetailId, final long customerId,
                       final ProductEntity productEntity, final int quantity) {
        this(orderId, orderDetailId, null, customerId, productEntity, quantity);
    }

    public long getOrderId() {
        return orderId;
    }

    public long getOrderDetailId() {
        return orderDetailId;
    }

    public long getCartId() {
        return cartId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public int getQuantity() {
        return quantity;
    }
}
