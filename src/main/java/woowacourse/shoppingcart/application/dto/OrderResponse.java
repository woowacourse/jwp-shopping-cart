package woowacourse.shoppingcart.application.dto;

import java.util.List;
import java.util.stream.Collectors;

import woowacourse.shoppingcart.domain.order.Order;

public class OrderResponse {

    private final Long id;
    private final List<OrderDetailResponse> orderDetails;

    private OrderResponse() {
        this(null, null);
    }

    public OrderResponse(final Long id, final List<OrderDetailResponse> orderDetails) {
        this.id = id;
        this.orderDetails = orderDetails;
    }

    public static OrderResponse from(Order order) {
        return new OrderResponse(
            order.getId(),
            order.getOrderDetails()
                .stream()
                .map(OrderDetailResponse::from)
                .collect(Collectors.toList())
        );
    }

    public Long getId() {
        return id;
    }

    public List<OrderDetailResponse> getOrderDetails() {
        return orderDetails;
    }
}
