package woowacourse.shoppingcart.dto;

import java.util.List;

public class OrderResponses {
    private List<OrderResponse> orders;

    public OrderResponses() {
    }

    public OrderResponses(List<OrderResponse> orders) {
        this.orders = orders;
    }

    public List<OrderResponse> getOrders() {
        return orders;
    }
}
