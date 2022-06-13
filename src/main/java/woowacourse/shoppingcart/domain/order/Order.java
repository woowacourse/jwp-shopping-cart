package woowacourse.shoppingcart.domain.order;

import java.util.List;
import java.util.stream.Collectors;

import woowacourse.shoppingcart.domain.cart.Cart;

public class Order {

    private final Long id;
    private final Long customerId;
    private final Cart cart;

    public Order(Long customerId, Cart cart) {
        this(null, customerId, cart);
    }

    public Order(Long id, Long customerId, Cart cart) {
        this.id = id;
        this.customerId = customerId;
        this.cart = cart;
    }

    public List<OrderItem> getOrderItems() {
        return cart.getValue().stream()
            .map(OrderItem::from)
            .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }
}
