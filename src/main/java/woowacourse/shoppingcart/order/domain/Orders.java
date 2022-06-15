package woowacourse.shoppingcart.order.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Orders {

    private static final long TEMPORARY_ID = 0;

    private final long id;
    private final long customerId;
    private final List<OrderDetail> orderDetails;
    private final LocalDateTime orderDateTime;

    public Orders(final long id, final long customerId, final List<OrderDetail> orderDetails,
                  final LocalDateTime orderDateTime) {
        this.id = id;
        this.customerId = customerId;
        this.orderDetails = orderDetails;
        this.orderDateTime = orderDateTime;
    }

    public Orders(final long customerId, final List<OrderDetail> orderDetails) {
        this(TEMPORARY_ID, customerId, orderDetails, LocalDateTime.now());
    }

    public long getId() {
        return id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }
}
