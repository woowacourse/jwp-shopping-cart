package woowacourse.shoppingcart.dto;

import java.util.List;

public class OrdersResponse {

    private final Long id;
    private final List<OrderDetailResponse> orderDetailResponses;

    public OrdersResponse(final Long id, final List<OrderDetailResponse> orderDetailResponses) {
        this.id = id;
        this.orderDetailResponses = orderDetailResponses;
    }

    public Long getId() {
        return id;
    }

    public List<OrderDetailResponse> getOrderDetails() {
        return orderDetailResponses;
    }
}
