package woowacourse.shoppingcart.dto;

import java.util.List;
import java.util.stream.Collectors;

import woowacourse.shoppingcart.domain.order.Orders;

public class OrderResponse {
    private final long id;
    private final List<OrderedProductDto> orderedProducts;

    public OrderResponse(long id, List<OrderedProductDto> orderedProducts) {
        this.id = id;
        this.orderedProducts = orderedProducts;
    }

    public static OrderResponse from(Orders orders) {
        return new OrderResponse(orders.getId(), orders.getOrderDetails().stream()
            .map(OrderedProductDto::from)
            .collect(Collectors.toList()));
    }

    public long getId() {
        return id;
    }

    public List<OrderedProductDto> getOrderedProducts() {
        return orderedProducts;
    }
}
