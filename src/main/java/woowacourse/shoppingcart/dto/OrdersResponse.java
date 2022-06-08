package woowacourse.shoppingcart.dto;

import java.util.List;

public class OrdersResponse {

    private final Long id;
    private final List<OrderDetailsResponse> orderDetails;
    private final Integer quantity;

    public OrdersResponse() {
        this(null, null, null);
    }

    public OrdersResponse(Long id, List<OrderDetailsResponse> orderDetails, Integer quantity) {
        this.id = id;
        this.orderDetails = orderDetails;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public List<OrderDetailsResponse> getOrderDetails() {
        return orderDetails;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
