package woowacourse.shoppingcart.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Orders {
    private final List<OrderDetail> orderDetails;

    public Orders(final List<OrderDetail> orderDetails) {
        this.orderDetails = new ArrayList<>(orderDetails);
    }

    public List<OrderDetail> getOrderDetails() {
        return Collections.unmodifiableList(orderDetails);
    }
}
