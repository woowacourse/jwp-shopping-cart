package woowacourse.shoppingcart.dto.order;

import java.util.List;

public class OrderSaveRequests {

    private List<OrderRequest> orders;

    private OrderSaveRequests() {
    }

    public OrderSaveRequests(final List<OrderRequest> orders) {
        this.orders = orders;
    }

    public List<OrderRequest> getOrders() {
        return orders;
    }
}
