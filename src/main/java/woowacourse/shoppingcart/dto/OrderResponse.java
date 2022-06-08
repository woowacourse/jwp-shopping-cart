package woowacourse.shoppingcart.dto;

import java.util.Date;
import java.util.List;

public class OrderResponse {

    private Long id;
    private List<OrderDetailResponse> orderDetails;
    private int totalPrice;
    private Date orderDate;

    public OrderResponse() {
    }

    public OrderResponse(Long id, List<OrderDetailResponse> orderDetails, int totalPrice,
                         Date orderDate) {
        this.id = id;
        this.orderDetails = orderDetails;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
    }

    public Long getId() {
        return id;
    }

    public List<OrderDetailResponse> getOrderDetails() {
        return orderDetails;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public Date getOrderDate() {
        return orderDate;
    }
}
