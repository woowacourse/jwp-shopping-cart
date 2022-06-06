package woowacourse.shoppingcart.domain;

import java.util.List;

public class Orders {

    private Long id;
    private List<OrderDetail> orderDetails;

    public Orders() {
    }

    public Orders(final Long id, final List<OrderDetail> orderDetails) {
        this.id = id;
        this.orderDetails = orderDetails;
    }

    public Long getId() {
        return id;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }
}
