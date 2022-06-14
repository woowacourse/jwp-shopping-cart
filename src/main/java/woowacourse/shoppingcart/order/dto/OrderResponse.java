package woowacourse.shoppingcart.order.dto;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.order.domain.Orders;

public class OrderResponse {

    private final Long id;
    private final List<OrderDetailResponse> orderDetails;

    private OrderResponse(final Long id, final List<OrderDetailResponse> orderDetails) {
        this.id = id;
        this.orderDetails = orderDetails;
    }

    public static OrderResponse from(final Orders orders) {
        final List<OrderDetailResponse> orderDetails = orders.getOrderDetails()
                .stream()
                .map(OrderDetailResponse::from)
                .collect(Collectors.toList());
        return new OrderResponse(orders.getId(), orderDetails);
    }

    public Long getId() {
        return id;
    }

    public List<OrderDetailResponse> getOrderDetails() {
        return orderDetails;
    }
}
