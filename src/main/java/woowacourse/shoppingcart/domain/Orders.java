package woowacourse.shoppingcart.domain;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.dto.response.OrderDetailResponse;
import woowacourse.shoppingcart.dto.response.OrdersResponse;

public class Orders {

    private final Long id;
    private final List<OrderDetail> orderDetails;

    public Orders(final Long id, final List<OrderDetail> orderDetails) {
        this.id = id;
        this.orderDetails = orderDetails;
    }

    public OrdersResponse toOrdersResponse() {
        List<OrderDetailResponse> orderDetailResponses = orderDetails.stream()
                .map(orderDetail -> new OrderDetailResponse(
                        orderDetail.getProductId(), orderDetail.getQuantity(),
                        orderDetail.getPrice(), orderDetail.getName(), orderDetail.getImageUrl()))
                .collect(Collectors.toUnmodifiableList());

        return new OrdersResponse(id, totalPrice(), orderDetailResponses);
    }

    private int totalPrice() {
        return orderDetails.stream()
                .mapToInt(orderDetail -> orderDetail.getPrice() * orderDetail.getQuantity())
                .sum();
    }

    public Long getId() {
        return id;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }
}
