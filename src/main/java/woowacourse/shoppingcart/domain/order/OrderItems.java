package woowacourse.shoppingcart.domain.order;

import java.util.List;

public class OrderItems {

    private final List<OrderItem> orderItems;

    public OrderItems(List<OrderItem> orderItems) {
        this.orderItems = List.copyOf(orderItems);
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}
