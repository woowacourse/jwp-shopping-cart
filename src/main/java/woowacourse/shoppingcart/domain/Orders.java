package woowacourse.shoppingcart.domain;

import java.util.List;

public class Orders {

    private Long id;
    private List<Order> orders;

    public Orders() {
    }

    public Orders(final Long id, final List<Order> orders) {
        this.id = id;
        this.orders = orders;
    }

    public Long getId() {
        return id;
    }

    public List<Order> getOrderDetails() {
        return orders;
    }
}
