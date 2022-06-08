package woowacourse.shoppingcart.dto;

import java.util.List;

public class OrdersResponse {

    private Long id;
    private List<OrderDetailResponse> orderDetails;

    private OrdersResponse() {
    }

    public OrdersResponse(final Long id, final List<OrderDetailResponse> orderDetails) {
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
