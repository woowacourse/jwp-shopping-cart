package woowacourse.shoppingcart.domain.order;

import java.util.List;

public class OrderItems {

    private final List<OrderItem> value;

    public OrderItems(List<OrderItem> value) {
        this.value = List.copyOf(value);
    }

    public List<OrderItem> getValue() {
        return value;
    }
}
