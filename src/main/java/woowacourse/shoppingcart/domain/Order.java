package woowacourse.shoppingcart.domain;

import java.util.List;

public class Order {

    private Long id;
    private List<OrderDetail> orderDetails;

    private Order() {
    }

    public Order(final Long id, final List<OrderDetail> orderDetails) {
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
