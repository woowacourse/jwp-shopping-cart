package woowacourse.shoppingcart.dto;

import java.util.Collections;
import java.util.List;

public class OrdersRequestDto {

    private List<Long> productIds;

    private OrdersRequestDto() {
    }

    public OrdersRequestDto(final List<Long> productIds) {
        this.productIds = productIds;
    }

    public List<Long> getProductIds() {
        return Collections.unmodifiableList(productIds);
    }
}
