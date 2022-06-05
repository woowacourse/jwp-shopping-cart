package woowacourse.shoppingcart.dto.order;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Orders;

public class OrderResponse {

    private OrderResponseNested order;

    private OrderResponse() {
    }

    public OrderResponse(final OrderResponseNested order) {
        this.order = order;
    }

    public static OrderResponse from(final Orders orders) {
        return new OrderResponse(OrderResponseNested.from(orders));
    }

    public OrderResponseNested getOrder() {
        return order;
    }

    public static class OrderResponseNested {

        private Long id;
        private List<OrderDetailResponse> orderDetails;

        private OrderResponseNested() {
        }

        public OrderResponseNested(final Long id, final List<OrderDetailResponse> orderDetails) {
            this.id = id;
            this.orderDetails = orderDetails;
        }

        public static OrderResponseNested from(final Orders order) {
            return new OrderResponseNested(
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

    static class OrderDetailResponse {

        private Long id;
        private Long productId;
        private String name;
        private int price;
        private int quantity;
        private String imageURL;

        private OrderDetailResponse() {
        }

        private OrderDetailResponse(final Long id, final Long productId, final String name, final int price,
                                    final int quantity, final String imageURL) {
            this.id = id;
            this.productId = productId;
            this.name = name;
            this.price = price;
            this.quantity = quantity;
            this.imageURL = imageURL;
        }

        private static OrderDetailResponse from(final OrderDetail orderDetail) {
            return new OrderDetailResponse(
                    orderDetail.getId(),
                    orderDetail.getProductId(),
                    orderDetail.getName(),
                    orderDetail.getPrice(),
                    orderDetail.getQuantity(),
                    orderDetail.getImageUrl()
            );
        }

        public Long getId() {
            return id;
        }

        public Long getProductId() {
            return productId;
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }

        public int getQuantity() {
            return quantity;
        }

        public String getImageURL() {
            return imageURL;
        }
    }
}
