package woowacourse.shoppingcart.dto.response;

import java.util.List;
import woowacourse.shoppingcart.domain.Orders;

public class OrdersResponse {

    private Long id;
    private List<OrderDetailsResponse> orderDetails;

    public OrdersResponse() {
    }

    public OrdersResponse(Long id,
        List<OrderDetailsResponse> orderDetails) {
        this.id = id;
        this.orderDetails = orderDetails;
    }

    public static OrdersResponse of(Orders orders) {
        return new OrdersResponse(
            orders.getId(),
            OrderDetailsResponse.of(orders.getOrderDetails())
        );
    }

    public Long getId() {
        return id;
    }

    public List<OrderDetailsResponse> getOrderDetails() {
        return orderDetails;
    }
}
