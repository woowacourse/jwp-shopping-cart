package woowacourse.shoppingcart.domain;

import java.util.List;

public class Order {

    private final Long id;
    private final List<OrderDetail> orderDetails;

    public Order(final Long id, final List<OrderDetail> orderDetails) {
        this.id = id;
        this.orderDetails = orderDetails;
    }

    public Long getId() {
        return id;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public int calculateRealTotalPrice() {
        return orderDetails.stream()
                .mapToInt(orderDetail -> orderDetail.getProduct().getPrice() * orderDetail.getQuantity())
                .sum();
    }
}
