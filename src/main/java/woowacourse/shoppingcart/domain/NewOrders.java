package woowacourse.shoppingcart.domain;

import java.util.List;

public class NewOrders {

    private Long id;

    private final List<NewOrderDetail> orderDetails;

    public NewOrders(List<NewOrderDetail> orderDetails) {
        this(null, orderDetails);
    }

    public NewOrders(Long id, List<NewOrderDetail> orderDetails) {
        this.id = id;
        this.orderDetails = orderDetails;
    }

    public Long getId() {
        return id;
    }

    public List<NewOrderDetail> getOrderDetails() {
        return orderDetails;
    }
}
