package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Order;
import woowacourse.shoppingcart.domain.OrderDetail;

import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {

    private final long orderId;
    private final List<OrderDetailResponse> order;
    private final int totalCost;

    public OrderResponse(long orderId, List<OrderDetailResponse> order, int totalCost) {
        this.order = order;
        this.orderId = orderId;
        this.totalCost = totalCost;
    }

    public static OrderResponse of(final Order order) {
        final List<OrderDetail> orderDetails = order.getOrderDetails();
        final List<OrderDetailResponse> orderDetailResponses = orderDetails.stream()
                .map(OrderDetailResponse::of)
                .collect(Collectors.toList());
        return new OrderResponse(order.getId(), orderDetailResponses, order.calculateRealTotalPrice());
    }

    public List<OrderDetailResponse> getOrder() {
        return order;
    }

    public long getOrderId() {
        return orderId;
    }

    public int getTotalCost() {
        return totalCost;
    }
}
