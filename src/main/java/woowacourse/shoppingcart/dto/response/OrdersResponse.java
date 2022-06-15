package woowacourse.shoppingcart.dto.response;

import java.util.List;

public class OrdersResponse {

    private Long id;
    private Integer totalPrice;
    private List<OrderDetailResponse> orderDetails;

    public OrdersResponse() {
    }

    public OrdersResponse(Long id, Integer totalPrice,
                          List<OrderDetailResponse> orderDetails) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.orderDetails = orderDetails;
    }

    public Long getId() {
        return id;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public List<OrderDetailResponse> getOrderDetails() {
        return orderDetails;
    }
}
