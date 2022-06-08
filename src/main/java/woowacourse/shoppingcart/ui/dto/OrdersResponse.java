package woowacourse.shoppingcart.ui.dto;

import java.util.List;
import woowacourse.shoppingcart.application.dto.OrderResponse;

public class OrdersResponse {

    private final List<OrderResponse> orders;

    public OrdersResponse(List<OrderResponse> orders) {
        this.orders = orders;
    }

    public List<OrderResponse> getOrders() {
        return orders;
    }
}
