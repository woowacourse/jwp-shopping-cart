package woowacourse.shoppingcart.application.dto;

import java.util.List;
import woowacourse.shoppingcart.domain.OrderDetail;

public class OrderDetailsServiceResponse {

    private final int totalPrice;
    private final Long id;
    private final List<OrderDetail> orderDetails;

    public OrderDetailsServiceResponse(final int totalPrice, final Long id,
                                       final List<OrderDetail> orderDetails) {
        this.totalPrice = totalPrice;
        this.id = id;
        this.orderDetails = orderDetails;
    }

    public static OrderDetailsServiceResponse from(final Long orderId, final List<OrderDetail> ordersDetails,
                                                   final int totalPrice) {
        return new OrderDetailsServiceResponse(totalPrice, orderId, ordersDetails);
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public Long getId() {
        return id;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }
}
