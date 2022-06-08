package woowacourse.shoppingcart.order.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Orders {

    private final Long id;
    private final Long customerId;
    private final List<OrderDetail> orderDetails;
    private final LocalDateTime orderDateTime;

    public Orders(final Long id, final Long customerId, final List<OrderDetail> orderDetails,
                  final LocalDateTime orderDateTime) {
        this.id = id;
        this.customerId = customerId;
        this.orderDetails = orderDetails;
        this.orderDateTime = orderDateTime;
    }

    public Orders(final Long customerId, final List<OrderDetail> orderDetails) {
        this(null, customerId, orderDetails, LocalDateTime.now());
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }
}
