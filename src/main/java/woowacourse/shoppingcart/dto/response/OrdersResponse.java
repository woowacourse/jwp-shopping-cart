package woowacourse.shoppingcart.dto.response;

import java.util.List;
import woowacourse.shoppingcart.domain.OrderDetail;

public class OrdersResponse {

    private final Long orderId;
    private final List<OrderResponse> order;

    public OrdersResponse(final Long orderId, final List<OrderResponse> order) {
        this.orderId = orderId;
        this.order = order;
    }

    public Long getOrderId() {
        return orderId;
    }

    public List<OrderResponse> getOrder() {
        return order;
    }
}
