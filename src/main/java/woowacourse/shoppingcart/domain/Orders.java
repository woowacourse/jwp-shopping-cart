package woowacourse.shoppingcart.domain;

import java.util.List;

public class Orders {

    private Long id;
    private List<OrdersDetail> orderDetails;

    private Orders() {
    }

    public Orders(final Long id, final List<OrdersDetail> orderDetails) {
        this.id = id;
        this.orderDetails = orderDetails;
    }

    public Long getId() {
        return id;
    }

    public List<OrdersDetail> getOrderDetails() {
        return orderDetails;
    }
}
