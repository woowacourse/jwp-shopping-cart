package woowacourse.shoppingcart.dto;

import java.util.ArrayList;
import java.util.List;

public class OrdersResponse {

    private Long orderId;
    private List<OrderDetailResponse> orderDetails;

    private OrdersResponse() {
    }

    public OrdersResponse(final Long orderId, final List<OrderDetailResponse> orderDetails) {
        this.orderId = orderId;
        this.orderDetails = new ArrayList<>(orderDetails);
    }

    public Long getOrderId() {
        return orderId;
    }

    public List<OrderDetailResponse> getOrderDetails() {
        return orderDetails;
    }
}
