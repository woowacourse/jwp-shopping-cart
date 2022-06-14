package woowacourse.shoppingcart.dto;

import java.util.List;
import woowacourse.shoppingcart.domain.Orders;

public class OrdersResponse {

    private Long id;
    private List<OrderDetailResponse> orderDetailResponses;

    private OrdersResponse() {
    }

    public OrdersResponse(Long id, List<OrderDetailResponse> orderDetailResponses) {
        this.id = id;
        this.orderDetailResponses = orderDetailResponses;
    }

    public static OrdersResponse of(Orders orders) {
        return new OrdersResponse(orders.getId(), OrderDetailResponse.of(orders.getOrderDetails()));
    }

    public Long getId() {
        return id;
    }

    public List<OrderDetailResponse> getOrderDetailResponses() {
        return orderDetailResponses;
    }
}
