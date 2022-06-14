package woowacourse.shoppingcart.domain;

import java.util.List;

public class Orders {

    private final Long id;
    private final List<OrderDetail> orderDetails;

    public Orders(final Long id, final List<OrderDetail> orderDetails) {
        this.id = id;
        this.orderDetails = orderDetails;
    }

    public Long calculateTotalPrice() {
        long result = 0;
        for (OrderDetail orderDetail : orderDetails) {
            result += orderDetail.calculateTotalPrice();
        }
        return result;
    }

    public Long getId() {
        return id;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }
}
