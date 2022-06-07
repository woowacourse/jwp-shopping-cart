package woowacourse.shoppingcart.dto.order;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.dto.order.OrderResponse.OrderResponseNested;

public class OrdersResponse {

    private List<OrderResponseNested> orders;

    private OrdersResponse() {
    }

    private OrdersResponse(final List<OrderResponseNested> orders) {
        this.orders = orders;
    }

    public static OrdersResponse from(final List<Orders> orders) {
        return new OrdersResponse(orders.stream()
                .map(OrderResponseNested::from)
                .collect(Collectors.toList()));
    }

    public List<OrderResponseNested> getOrders() {
        return orders;
    }
}
