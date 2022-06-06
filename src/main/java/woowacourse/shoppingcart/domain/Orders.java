package woowacourse.shoppingcart.domain;

import java.util.List;
import woowacourse.shoppingcart.domain.customer.Customer;

public class Orders {

    private Long id;
    private Customer customer;
    private List<OrderDetail> orderDetails;

    public Orders() {
    }

    public Orders(Long id, Customer customer, List<OrderDetail> orderDetails) {
        this.id = id;
        this.customer = customer;
        this.orderDetails = orderDetails;
    }

    public Long getId() {
        return id;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }
}
