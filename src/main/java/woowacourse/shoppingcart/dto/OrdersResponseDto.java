package woowacourse.shoppingcart.dto;

import java.util.Collections;
import java.util.List;

public class OrdersResponseDto {

    private List<OrdersDetailDto> ordersDetails;

    private OrdersResponseDto() {
    }

    public OrdersResponseDto(final List<OrdersDetailDto> ordersDetails) {
        this.ordersDetails = ordersDetails;
    }

    public List<OrdersDetailDto> getOrdersDetails() {
        return Collections.unmodifiableList(ordersDetails);
    }
}
