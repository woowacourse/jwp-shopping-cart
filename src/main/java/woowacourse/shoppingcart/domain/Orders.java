package woowacourse.shoppingcart.domain;

import java.util.List;

public class Orders {
    private List<OrderDetail> orderDetails;
    private Long customerId;
    private int totalPrice;

    public Orders() {
    }

    public Orders(List<OrderDetail> orderDetails, Long customerId) {
        this.orderDetails = orderDetails;
        this.customerId = customerId;
        this.totalPrice = orderDetails.stream()
                .mapToInt(orderDetail -> orderDetail.getProduct().getPrice())
                .sum();
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orderDetails=" + orderDetails +
                ", customerId=" + customerId +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
