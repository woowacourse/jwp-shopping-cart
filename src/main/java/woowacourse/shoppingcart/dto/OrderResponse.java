package woowacourse.shoppingcart.dto;

import java.util.List;

public class OrderResponse {

    private Long id;
    private List<OrderDetailResponse> orderDetails;

    private OrderResponse() {
    }

    public OrderResponse(final Long id, final List<OrderDetailResponse> orderDetails) {
        this.id = id;
        this.orderDetails = orderDetails;
    }

    public Long getId() {
        return id;
    }

    public List<OrderDetailResponse> getOrderDetails() {
        return orderDetails;
    }
}
