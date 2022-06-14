package woowacourse.shoppingcart.domain;

import java.util.Collections;
import java.util.List;

public class Orders {

    private final Long id;
    private final Long customerId;
    private final List<OrdersDetail> ordersDetails;

    public Orders(final Long id, final Long customerId, final List<OrdersDetail> ordersDetails) {
        this.id = id;
        this.customerId = customerId;
        this.ordersDetails = ordersDetails;
    }

    public static Orders createWithoutId(final Long customerId, final List<OrdersDetail> ordersDetails) {
        return new Orders(null, customerId, ordersDetails);
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public List<OrdersDetail> getOrdersDetails() {
        return Collections.unmodifiableList(ordersDetails);
    }
}
