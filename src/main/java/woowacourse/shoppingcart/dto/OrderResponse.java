package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Orders;
import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {

    private final int totalPrice;
    private final long orderId;
    private final List<OrderDetailResponse> orderDetailResponses;

    private OrderResponse(final int totalPrice, final Long orderId, final List<OrderDetailResponse> orderDetailResponses) {
        this.totalPrice = totalPrice;
        this.orderId = orderId;
        this.orderDetailResponses = orderDetailResponses;
    }

    public static OrderResponse from(final Orders orders) {
        final List<OrderDetailResponse> orderDetailResponseDtos =
                orders.getOrderDetails().stream()
                        .map(OrderDetailResponse::from)
                        .collect(Collectors.toList());

        return new OrderResponse(orders.calculatePrice(), orders.getId(), orderDetailResponseDtos);
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public Long getOrderId() {
        return orderId;
    }

    public List<OrderDetailResponse> getOrderDetailResponses() {
        return orderDetailResponses;
    }
}
