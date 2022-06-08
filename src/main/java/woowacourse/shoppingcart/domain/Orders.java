package woowacourse.shoppingcart.domain;

import static lombok.EqualsAndHashCode.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class Orders {

    @Include
    private Long id;
    private final Long customerId;
    private final List<OrderDetail> orderDetails;
    private final LocalDateTime orderDate;

    public Orders(Long customerId, List<CartItem> cartItems) {
        this.customerId = customerId;
        this.orderDetails = cartItems.stream()
            .map(OrderDetail::new)
            .collect(Collectors.toList());
        this.orderDate = LocalDateTime.now();
    }

    public Orders createWithId(Long id) {
        return new Orders(id, customerId, orderDetails, orderDate);
    }

    public int calculateTotalPrice() {
        return orderDetails.stream()
            .mapToInt(OrderDetail::calculatePrice)
            .sum();
    }

    public String getOrderDate() {
        return orderDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
