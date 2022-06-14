package woowacourse.shoppingcart.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Orders {

    private final Long id;
    private final List<OrderDetail> orderDetails;
    private final LocalDateTime date;

    public Orders(Long id, List<OrderDetail> orderDetails, LocalDateTime date) {
        this.id = id;
        this.orderDetails = orderDetails;
        this.date = date;
    }

    public int calculateTotalPrice() {
        return orderDetails.stream()
                .mapToInt(OrderDetail::calculatePrice)
                .sum();
    }

    public Long getId() {
        return id;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
