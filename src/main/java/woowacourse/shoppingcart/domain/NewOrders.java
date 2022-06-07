package woowacourse.shoppingcart.domain;

import java.util.List;

public class NewOrders {

    private final long id;

    private final List<NewOrderDetail> orderDetails;

    public NewOrders(long id, List<NewOrderDetail> orderDetails) {
        this.id = id;
        this.orderDetails = orderDetails;
    }

    public long getId() {
        return id;
    }

    public List<NewOrderDetail> getOrderDetails() {
        return orderDetails;
    }
}
