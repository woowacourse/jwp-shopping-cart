package woowacourse.shoppingcart.ui.order.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.application.dto.OrderDetailsServiceResponse;
import woowacourse.shoppingcart.domain.OrderDetail;

public class OrderDetailsResponse {

    private int totalPrice;
    private Long id;
    private List<OrderDetailResponse> orderDetails;

    public OrderDetailsResponse() {
    }

    public OrderDetailsResponse(final int totalPrice, final Long id,
                                final List<OrderDetailResponse> orderDetails) {
        this.totalPrice = totalPrice;
        this.id = id;
        this.orderDetails = orderDetails;
    }

    public static OrderDetailsResponse from(final OrderDetailsServiceResponse serviceResponse) {
        final List<OrderDetail> orderDetails = serviceResponse.getOrderDetails();
        final List<OrderDetailResponse> orderDetailResponses = orderDetails.stream()
                .map(OrderDetailResponse::from)
                .collect(Collectors.toList());

        return new OrderDetailsResponse(serviceResponse.getTotalPrice(), serviceResponse.getId(), orderDetailResponses);
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public Long getId() {
        return id;
    }

    public List<OrderDetailResponse> getOrderDetails() {
        return orderDetails;
    }
}
