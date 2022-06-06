package woowacourse.shoppingcart.application.dto;

import java.util.List;

public class OrderResponse {

    private final Long orderId;
    private final List<OrderDetailResponse> order;

    public OrderResponse(Long orderId, List<OrderDetailResponse> order) {
        this.orderId = orderId;
        this.order = order;
    }

    public Long getOrderId() {
        return orderId;
    }

    public List<OrderDetailResponse> getOrder() {
        return order;
    }
}
