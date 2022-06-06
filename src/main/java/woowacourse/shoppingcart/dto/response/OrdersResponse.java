package woowacourse.shoppingcart.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.dto.request.OrderRequest;

public class OrdersResponse {

    private Long id;
    private List<OrderDetailResponse> orderDetails;

    private OrdersResponse() {}

    private OrdersResponse(final Long id, final List<OrderDetailResponse> orderDetails) {
        this.id = id;
        this.orderDetails = orderDetails;
    }

    public static OrdersResponse of(final Long orderId, final List<OrderDetail> orderDetails) {
        return new OrdersResponse(orderId, orderDetails.stream()
                .map(OrderDetailResponse::from)
                .collect(Collectors.toList()));
    }

    public Long getId() {
        return id;
    }

    public List<OrderDetailResponse> getOrderDetails() {
        return orderDetails;
    }
}
