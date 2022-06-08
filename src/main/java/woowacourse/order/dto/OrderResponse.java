package woowacourse.order.dto;

import java.util.List;
import java.util.stream.Collectors;

import woowacourse.order.domain.Orders;

public class OrderResponse {

    private InnerOrderResponse order;

    private OrderResponse() {
    }

    public OrderResponse(final InnerOrderResponse order) {
        this.order = order;
    }

    public static OrderResponse from(final Orders orders) {
        return new OrderResponse(InnerOrderResponse.from(orders));
    }

    public InnerOrderResponse getOrder() {
        return order;
    }

    public static class InnerOrderResponse {

        private Long id;
        private List<OrderDetailResponse> orderDetails;

        private InnerOrderResponse() {
        }

        public InnerOrderResponse(final Long id, final List<OrderDetailResponse> orderDetails) {
            this.id = id;
            this.orderDetails = orderDetails;
        }

        public static InnerOrderResponse from(final Orders orders) {
            final List<OrderDetailResponse> orderDetailResponses = orders.getOrderDetails()
                .stream()
                .map(OrderDetailResponse::from)
                .collect(Collectors.toList());
            return new InnerOrderResponse(orders.getId(), orderDetailResponses);
        }

        public Long getId() {
            return id;
        }

        public List<OrderDetailResponse> getOrderDetails() {
            return orderDetails;
        }
    }
}
