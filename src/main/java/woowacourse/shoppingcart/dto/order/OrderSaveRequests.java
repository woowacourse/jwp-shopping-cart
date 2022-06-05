package woowacourse.shoppingcart.dto.order;

import java.util.List;

public class OrderSaveRequests {

    private List<OrderSaveRequest> orders;

    private OrderSaveRequests() {
    }

    public OrderSaveRequests(final List<OrderSaveRequest> orders) {
        this.orders = orders;
    }

    public List<OrderSaveRequest> getOrders() {
        return orders;
    }
}
