package woowacourse.shoppingcart.domain.order;

import java.util.List;
import java.util.stream.Collectors;

import woowacourse.shoppingcart.domain.cart.Cart;

public class Order {

    private final Long id;
    private final Long customerId;
    private final OrderItems orderItems;

    public Order(Long customerId, List<OrderItem> orderItems) {
        this(null, customerId, orderItems);
    }

    public Order(Long customerId, Cart cart) {
        this(null, customerId, cart);
    }

    public Order(Long id, Long customerId, List<OrderItem> orderItems) {
        this.id = id;
        this.customerId = customerId;
        this.orderItems = new OrderItems(orderItems);
    }

    public Order(Long id, Long customerId, Cart cart) {
        this(id, customerId, cart.getValue().stream()
            .map(OrderItem::from)
            .collect(Collectors.toList()));
    }

    public List<OrderItem> getOrderItems() {
        return orderItems.getValue();
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }
}
