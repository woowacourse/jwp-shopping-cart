package woowacourse.shoppingcart.domain.order;

import java.util.List;

public class Orders {

    private Long id;

    private final List<OrderDetail> orderDetails;

    public Orders(List<OrderDetail> orderDetails) {
        this(null, orderDetails);
    }

    public Orders(Long id, List<OrderDetail> orderDetails) {
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
