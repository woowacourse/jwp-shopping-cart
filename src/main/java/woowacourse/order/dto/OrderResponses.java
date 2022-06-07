package woowacourse.order.dto;

import java.util.List;
import java.util.stream.Collectors;

public class OrderResponses {

    private List<OrderInnerResponse> orders;

    private OrderResponses() {
    }

    public OrderResponses(final List<OrderInnerResponse> orders) {
        this.orders = orders;
    }

    public static OrderResponses from(final List<OrderResponse> orderResponses) {
        return new OrderResponses(OrderInnerResponse.from(orderResponses));
    }

    public List<OrderInnerResponse> getOrders() {
        return orders;
    }

    public static class OrderInnerResponse {

        private Long id;
        private List<OrderDetailResponse> orderDetails;

        private OrderInnerResponse() {
        }

        public OrderInnerResponse(final OrderResponse orderResponse) {
            this.id = orderResponse.getId();
            this.orderDetails = orderResponse.getOrderDetails();
        }

        public static List<OrderInnerResponse> from(final List<OrderResponse> orderResponses) {
            return orderResponses.stream()
                .map(OrderInnerResponse::new)
                .collect(Collectors.toList());
        }

        public Long getId() {
            return id;
        }

        public List<OrderDetailResponse> getOrderDetails() {
            return orderDetails;
        }
    }
}
