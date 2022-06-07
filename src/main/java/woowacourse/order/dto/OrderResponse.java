package woowacourse.order.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import woowacourse.order.domain.OrderDetail;

@JsonTypeName("order")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class OrderResponse {

    private Long id;
    private List<OrderDetailResponse> orderDetails;

    private OrderResponse() {
    }

    public OrderResponse(final Long id, final List<OrderDetailResponse> orderDetails) {
        this.id = id;
        this.orderDetails = orderDetails;
    }

    public static OrderResponse from(final Long id, final List<OrderDetail> orderDetails) {
        final List<OrderDetailResponse> orderDetailResponses = orderDetails.stream()
            .map(OrderDetailResponse::from)
            .collect(Collectors.toList());

        return new OrderResponse(id, orderDetailResponses);
    }

    public Long getId() {
        return id;
    }

    public List<OrderDetailResponse> getOrderDetails() {
        return orderDetails;
    }
}
