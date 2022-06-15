package woowacourse.shoppingcart.dto.response;

import java.util.List;

public class OrderResponse {

    private Long id;
    private List<OrderDetailResponse> orderDetails;

    private OrderResponse() {
    }

    public OrderResponse(Long id, List<OrderDetailResponse> orderDetails) {
        this.id = id;
        this.orderDetails = orderDetails;
    }

    public Long getId() {
        return id;
    }

    public List<OrderDetailResponse> getOrderDetails() {
        return orderDetails;
    }
}
