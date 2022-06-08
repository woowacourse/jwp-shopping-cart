package woowacourse.shoppingcart.application.dto;

import java.util.List;

public class OrderResponse {

    private final Long orderId;
    private final int totalCost;
    private final List<OrderDetailResponse> order;

    public OrderResponse(Long orderId, int totalCost, List<OrderDetailResponse> order) {
        this.orderId = orderId;
        this.totalCost = totalCost;
        this.order = order;
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
