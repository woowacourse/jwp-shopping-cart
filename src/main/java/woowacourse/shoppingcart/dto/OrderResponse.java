package woowacourse.shoppingcart.dto;

import java.util.List;

public class OrderResponse {

    private final long orderId;
    private final List<OrderDetailResponse> order;
    private final int totalCost;

    public OrderResponse(long orderId, List<OrderDetailResponse> order) {
        this.orderId = orderId;
        this.order = order;
        this.totalCost = order.stream().mapToInt(OrderDetailResponse::getCost).sum();
    }

    public long getOrderId() {
        return orderId;
    }

    public List<OrderDetailResponse> getOrder() {
        return order;
    }

    public int getTotalCost() {
        return totalCost;
    }
}
