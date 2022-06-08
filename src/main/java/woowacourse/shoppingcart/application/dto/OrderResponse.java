package woowacourse.shoppingcart.application.dto;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.order.OrderDetail;
import woowacourse.shoppingcart.domain.order.Orders;

public class OrderResponse {

    private final Long orderId;
    private final int totalCost;
    private final List<OrderDetailResponse> order;

    public OrderResponse(Long orderId, int totalCost, List<OrderDetailResponse> order) {
        this.orderId = orderId;
        this.totalCost = totalCost;
        this.order = order;
    }

    public static OrderResponse from(Orders orders) {
        return new OrderResponse(
                orders.getId(),
                orders.calculateTotalCost(),
                generateToOrderDetailResponses(orders.getOrderDetails())
        );
    }

    private static List<OrderDetailResponse> generateToOrderDetailResponses(List<OrderDetail> orderDetails) {
        return orderDetails.stream()
                .map(OrderDetailResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public Long getOrderId() {
        return orderId;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public List<OrderDetailResponse> getOrder() {
        return order;
    }
}
