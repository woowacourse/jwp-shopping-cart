package woowacourse.shoppingcart.application.dto;

import java.util.List;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Orders;

public class OrderDetailServiceResponse {

    private int totalPrice;
    private Long id;
    private List<OrderDetail> orderDetails;

    public OrderDetailServiceResponse() {
    }

    public OrderDetailServiceResponse(final int totalPrice, final Long id,
                                      final List<OrderDetail> orderDetails) {
        this.totalPrice = totalPrice;
        this.id = id;
        this.orderDetails = orderDetails;
    }

    public static OrderDetailServiceResponse from(final Orders orders, final int totalPrice) {
        return new OrderDetailServiceResponse(totalPrice, orders.getId(), orders.getOrderDetails());
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
