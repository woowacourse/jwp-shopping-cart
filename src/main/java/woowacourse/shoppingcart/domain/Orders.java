package woowacourse.shoppingcart.domain;

import java.util.List;

public class Orders {

    private final Long orderId;
    private final List<OrderDetail> order;

    public Orders(final Long orderId, final List<OrderDetail> order) {
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
