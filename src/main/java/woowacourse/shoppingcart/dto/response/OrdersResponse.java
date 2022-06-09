package woowacourse.shoppingcart.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.Orders;

public class OrdersResponse {

    private Long id;
    private List<OrderDetailResponse> orderDetails;

    private OrdersResponse() {
    }

    public OrdersResponse(final Orders orders) {
        this.id = orders.getId();
        this.orderDetails = orders.getOrderDetails().stream()
                .map(OrderDetailResponse::new)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public List<OrderDetailResponse> getOrderDetails() {
        return orderDetails;
    }
}
