package woowacourse.shoppingcart.dto;

import java.util.List;

import woowacourse.shoppingcart.domain.order.Order;
import woowacourse.shoppingcart.domain.order.OrderItem;

public class OrderResponse {

    private final long customerId;
    private final long orderId;
    private final List<OrderItem> orderItems;

    public OrderResponse(long customerId, long orderId, List<OrderItem> orderItems) {
        this.customerId = customerId;
        this.orderId = orderId;
        this.orderItems = orderItems;
    }

    public static OrderResponse from(Order order) {
        return new OrderResponse(order.getCustomerId(), order.getId(), order.getOrderItems());
    }

    public long getCustomerId() {
        return customerId;
    }

    public long getOrderId() {
        return orderId;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}
