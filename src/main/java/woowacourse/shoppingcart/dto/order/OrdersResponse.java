package woowacourse.shoppingcart.dto.order;

import java.util.List;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Orders;

public class OrdersResponse {

    private final Long id;
    private final Long totalPrice;
    private final List<OrderDetail> orderDetails;

    public OrdersResponse(final Long id,
                          final Long totalPrice,
                          final List<OrderDetail> orderDetails) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.orderDetails = orderDetails;
    }

    public static OrdersResponse from(Orders orders) {
        return new OrdersResponse(orders.getId(), orders.calculateTotalPrice(), orders.getOrderDetails());
    }

    public Long getId() {
        return id;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }
}
