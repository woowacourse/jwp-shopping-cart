package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Orders;

import java.util.List;

public class OrdersResponse {

    private final Long id;
    private final List<OrderDetail> orderDetails;

    private OrdersResponse(Long id, List<OrderDetail> orderDetails) {
        this.id = id;
        this.orderDetails = orderDetails;
    }

    public static OrdersResponse from(Orders orders) {
        return new OrdersResponse(orders.getId(), orders.getOrderDetails());
    }

    public Long getId() {
        return id;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }
}
