package woowacourse.shoppingcart.dto.order;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.dto.order.OrderResponse.OrderResponseNested;

public class OrderResponses {

    private List<OrderResponseNested> orders;

    private OrderResponses() {
    }

    private OrderResponses(final List<OrderResponseNested> orders) {
        this.orders = orders;
    }

    public static OrderResponses from(final List<Orders> orders) {
        return new OrderResponses(orders.stream()
                .map(OrderResponseNested::from)
                .collect(Collectors.toList()));
    }

    public List<OrderResponseNested> getOrders() {
        return orders;
    }
}
