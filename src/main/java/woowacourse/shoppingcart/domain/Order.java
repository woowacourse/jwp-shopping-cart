package woowacourse.shoppingcart.domain;

import java.util.List;

public class Order {
    private List<OrderProduct> orderProducts;
    private Long customerId;
    private int totalPrice;

    public Order() {
    }

    public Order(List<OrderProduct> orderProducts, Long customerId) {
        this.orderProducts = orderProducts;
        this.customerId = customerId;
        this.totalPrice = orderProducts.stream()
                .mapToInt(orderProduct -> orderProduct.getProduct().getPrice())
                .sum();
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderProducts=" + orderProducts +
                ", customerId=" + customerId +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
