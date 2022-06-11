package woowacourse.shoppingcart.dto;

import java.util.List;

public class OrdersResponse {

    private final long id;
    private final List<OrderDetailResponse> orderDetailResponses;

    public OrdersResponse(final long id, final List<OrderDetailResponse> orderDetailResponses) {
        this.id = id;
        this.orderDetailResponses = orderDetailResponses;
    }

    public long getId() {
        return id;
    }

    public List<OrderDetailResponse> getOrderDetailResponses() {
        return orderDetailResponses;
    }
}
