package woowacourse.shoppingcart.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {

    private Long id;
    private List<OrderDetailResponse> orderDetailResponses;
    private int totalPrice;
    private LocalDateTime localDateTime;

    public OrderResponse() {
    }

    public OrderResponse(Long id, List<OrderDetailResponse> orderDetailResponses, int totalPrice,
                         LocalDateTime localDateTime) {
        this.id = id;
        this.orderDetailResponses = orderDetailResponses;
        this.totalPrice = totalPrice;
        this.localDateTime = localDateTime;
    }

    public OrderResponse(Long id, List<OrderDetailResponse> orderDetailResponses) {
        this(id, orderDetailResponses, 0, null);
    }

    public Long getId() {
        return id;
    }

    public List<OrderDetailResponse> getOrderDetailResponses() {
        return orderDetailResponses;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
