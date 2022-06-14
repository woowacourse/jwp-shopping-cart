package woowacourse.shoppingcart.domain;

import java.util.List;

public class OrderResponse {

    private final Long orderId;
    private final List<OrderDetail> order;

    public OrderResponse(Long orderId, List<OrderDetail> order) {
        this.orderId = orderId;
        this.order = order;
    }

    public Long getOrderId() {
        return orderId;
    }

    public List<OrderDetail> getOrder() {
        return order;
    }
}
