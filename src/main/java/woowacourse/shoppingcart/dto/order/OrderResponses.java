package woowacourse.shoppingcart.dto.order;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.Orders;

public class OrderResponses {

    private List<OrderResponse> orders;

    private OrderResponses() {
    }

    private OrderResponses(final List<OrderResponse> orders) {
        this.orders = orders;
    }

    public static OrderResponses from(final List<Orders> orders) {
        return new OrderResponses(orders.stream()
                .map(OrderResponse::from)
                .collect(Collectors.toList()));
    }

    public List<OrderResponse> getOrders() {
        return orders;
    }
}
