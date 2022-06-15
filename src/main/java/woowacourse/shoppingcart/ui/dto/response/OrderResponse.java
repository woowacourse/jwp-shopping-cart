package woowacourse.shoppingcart.ui.dto.response;

import java.util.List;

public class OrderResponse {
    private long totalPrice;
    private long id;
    private List<OrderDetailResponse> orderDetails;

    public OrderResponse() {
    }

    public OrderResponse(final long totalPrice, final long id, final List<OrderDetailResponse> orderDetailResponses) {
        this.totalPrice = totalPrice;
        this.id = id;
        this.orderDetails = orderDetailResponses;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public long getId() {
        return id;
    }

    public List<OrderDetailResponse> getOrderDetails() {
        return orderDetails;
    }
}
