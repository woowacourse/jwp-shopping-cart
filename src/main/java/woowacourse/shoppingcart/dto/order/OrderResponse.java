package woowacourse.shoppingcart.dto.order;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.Orders;

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

    public static OrderResponse from(final Orders order) {
        return new OrderResponse(
                order.getId(),
                order.getOrderDetails()
                        .stream()
                        .map(OrderDetailResponse::from)
                        .collect(Collectors.toList())
        );
    }

    public Long getId() {
        return id;
    }

    public List<OrderDetailResponse> getOrderDetails() {
        return orderDetails;
    }
}
