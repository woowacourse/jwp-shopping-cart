package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Order;

import java.util.List;
import java.util.stream.Collectors;

public class OrdersResponse {

    private final List<OrderResponse> orders;

    public OrdersResponse(List<OrderResponse> orders) {
        this.orders = orders;
    }

    public static OrdersResponse of(List<Order> orders) {
        final List<OrderResponse> orderResponses = orders.stream()
                .map(OrderResponse::of)
                .collect(Collectors.toList());
        return new OrdersResponse(orderResponses);
    }

    public List<OrderResponse> getOrders() {
        return orders;
    }
}
