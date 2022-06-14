package woowacourse.shoppingcart.dto.order;

import java.time.LocalDateTime;
import java.util.List;

public class FindOrderResponse {

    private Long id;
    private List<OrderDetailDto> orderDetails;
    private int totalPrice;
    private LocalDateTime localDateTime;

    public FindOrderResponse() {
    }

    public FindOrderResponse(Long id, List<OrderDetailDto> orderDetails, int totalPrice,
                             LocalDateTime localDateTime) {
        this.id = id;
        this.orderDetails = orderDetails;
        this.totalPrice = totalPrice;
        this.localDateTime = localDateTime;
    }

    public Long getId() {
        return id;
    }

    public List<OrderDetailDto> getOrderDetails() {
        return orderDetails;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
