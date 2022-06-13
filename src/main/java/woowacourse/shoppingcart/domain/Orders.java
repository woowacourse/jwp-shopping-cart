package woowacourse.shoppingcart.domain;

import java.util.List;

public class Orders {

    private final long id;
    private final List<OrderDetail> orderDetails;

    public Orders(final long id, final List<OrderDetail> orderDetails) {
        this.id = id;
        this.orderDetails = orderDetails;
    }

    public long getId() {
        return id;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }
}
