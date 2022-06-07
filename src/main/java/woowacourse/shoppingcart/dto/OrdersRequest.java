package woowacourse.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

public class OrdersRequest {

    private final List<OrderDetailRequest> order;

    @JsonCreator
    public OrdersRequest(List<OrderDetailRequest> order) {
        this.order = order;
    }

    public List<OrderDetailRequest> getOrder() {
        return order;
    }
}
