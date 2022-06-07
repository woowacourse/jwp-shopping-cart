package woowacourse.shoppingcart.domain;

import java.util.List;

public class Order {

    private Long id;
    private List<OrderDetail> orderDetails;

    public Order(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Order(final Long id, final List<OrderDetail> orderDetails) {
        this(orderDetails);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }
}
