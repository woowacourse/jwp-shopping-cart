package woowacourse.order.dto;

import java.util.List;
import java.util.stream.Collectors;

import woowacourse.order.domain.Orders;
import woowacourse.order.dto.OrderResponse.InnerOrderResponse;

public class OrderResponses {

    private List<InnerOrderResponse> orders;

    private OrderResponses() {
    }

    public OrderResponses(final List<InnerOrderResponse> orders) {
        this.orders = orders;
    }

    public static OrderResponses from(final List<Orders> orders) {
        final List<InnerOrderResponse> orderResponses = orders.stream()
            .map(InnerOrderResponse::from)
            .collect(Collectors.toList());
        return new OrderResponses(orderResponses);
    }

    public List<InnerOrderResponse> getOrders() {
        return orders;
    }
}
