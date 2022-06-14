package woowacourse.shoppingcart.domain.order;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.dto.OrdersRequest;

public class Orders {

    private final long customerId;
    private final List<Order> orders;

    public Orders(long customerId, long orderId, OrdersRequest orders) {
        this.customerId = customerId;
        this.orders = orders.getOrder().stream()
                .map(order -> new Order(orderId, order.getId(), order.getQuantity()))
                .collect(Collectors.toList());
    }

    public long getCustomerId() {
        return customerId;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<Long> getProductIds() {
        return orders.stream()
                .map(Order::getProductId)
                .collect(Collectors.toList());
    }
}
