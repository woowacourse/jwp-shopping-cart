package woowacourse.shoppingcart.domain.order;

import java.util.List;
import java.util.stream.Collectors;

public class Orders {

    private final long customerId;
    private final List<Order> orders;

    public Orders(long customerId, long orderId, List<Order> orders) {
        this.customerId = customerId;
        this.orders = orders;
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
