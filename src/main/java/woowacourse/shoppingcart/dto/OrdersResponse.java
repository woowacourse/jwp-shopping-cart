package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Orders;

import java.util.List;
import java.util.stream.Collectors;

public class OrdersResponse {
    private final Long id;
    private final List<OrderDetailDto> orderDetailDtos;

    public OrdersResponse(Orders orders) {
        List<OrderDetailDto> orderDetailDtos = orders.getOrderDetails().stream()
                .map(OrderDetailDto::new)
                .collect(Collectors.toList());

        this.id = orders.getId();
        this.orderDetailDtos = orderDetailDtos;
    }

    public OrdersResponse(final Long id, final List<OrderDetailDto> orderDetailDtos) {
        this.id = id;
        this.orderDetailDtos = orderDetailDtos;
    }

    public Long getId() {
        return id;
    }

    public List<OrderDetailDto> getOrderDetailDtos() {
        return orderDetailDtos;
    }
}
