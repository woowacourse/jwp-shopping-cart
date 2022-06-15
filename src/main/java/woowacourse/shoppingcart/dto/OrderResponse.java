package woowacourse.shoppingcart.dto;

import java.util.List;

public class OrderResponse {

    private final long orderId;
    private final List<OrderDetailResponse> orderDetails;
    private final int totalCost;

    public OrderResponse(long orderId, List<OrderDetailResponse> orderDetails) {
        this.orderId = orderId;
        this.orderDetails = orderDetails;
        this.totalCost = orderDetails.stream().mapToInt(OrderDetailResponse::getCost).sum();
    }

    public long getOrderId() {
        return orderId;
    }

    public List<OrderDetailResponse> getOrderDetails() {
        return orderDetails;
    }

    public int getTotalCost() {
        return totalCost;
    }
}
