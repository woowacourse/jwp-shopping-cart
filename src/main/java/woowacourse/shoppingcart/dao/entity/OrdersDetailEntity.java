package woowacourse.shoppingcart.dao.entity;

import woowacourse.shoppingcart.domain.order.OrderDetail;

public class OrdersDetailEntity {

    private final Long id;
    private final Long ordersId;
    private final Long productId;
    private final int quantity;

    public OrdersDetailEntity(Long id, Long ordersId, Long productId, int quantity) {
        this.id = id;
        this.ordersId = ordersId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public static OrdersDetailEntity from(Long ordersId, OrderDetail orderDetail) {
        return new OrdersDetailEntity(
                null,
                ordersId,
                orderDetail.getProduct().getId(),
                orderDetail.getQuantity()
        );
    }

    public Long getId() {
        return id;
    }

    public Long getOrdersId() {
        return ordersId;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
