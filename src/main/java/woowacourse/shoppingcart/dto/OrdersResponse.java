package woowacourse.shoppingcart.dto;

import java.util.List;

public class OrdersResponse {

    private final List<OrderResponse> orders;


    public OrdersResponse(List<OrderResponse> orders) {
        this.orders = orders;
    }

    public List<OrderResponse> getOrders() {
        return orders;
    }
}
