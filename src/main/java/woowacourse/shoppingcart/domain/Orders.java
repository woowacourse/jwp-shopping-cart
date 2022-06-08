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

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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

    public Orders(Long id, Long customerId, List<OrderDetail> orderDetails, String orderDate) {
        this(id, customerId, orderDetails, LocalDateTime.parse(orderDate, FORMATTER));
    }

    public Orders createWithId(Long id) {
        return new Orders(id, customerId, orderDetails, orderDate);
    }

    public int calculateTotalPrice() {
        return orderDetails.stream()
            .mapToInt(OrderDetail::calculatePrice)
            .sum();
    }

    public boolean isSameCustomerId(Long customerId) {
        return this.customerId.equals(customerId);
    }

    public String getOrderDate() {
        return orderDate.format(FORMATTER);
    }
}
