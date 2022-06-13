package woowacourse.shoppingcart.dto.order;

import java.util.List;
import java.util.stream.Collectors;

public class OrdersResponse {

    private List<OrderInnerResponse> orders;

    private OrdersResponse() {
    }

    public OrdersResponse(List<OrderResponse> orderResponse) {
        this.orders = orderResponse.stream()
                .map(OrderInnerResponse::new)
                .collect(Collectors.toList());
    }

    public List<OrderInnerResponse> getOrders() {
        return orders;
    }

    public static class OrderInnerResponse {

        private long id;
        private List<OrderDetailResponse> orderDetails;

        private OrderInnerResponse() {
        }

        public OrderInnerResponse(OrderResponse orderResponse) {
            this.id = orderResponse.getId();
            this.orderDetails = orderResponse.getOrderDetails();
        }

        public long getId() {
            return id;
        }

        public List<OrderDetailResponse> getOrderDetails() {
            return orderDetails;
        }
    }
}
