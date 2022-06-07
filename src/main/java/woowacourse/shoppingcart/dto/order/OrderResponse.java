package woowacourse.shoppingcart.dto.order;

import java.util.List;
import woowacourse.shoppingcart.domain.OrderedProduct;

public class OrderResponse {
    private final Long id;
    private final List<OrderedProduct> orderedProduct;

    public OrderResponse(Long id, List<OrderedProduct> orderedProduct) {
        this.id = id;
        this.orderedProduct = orderedProduct;
    }

    public Long getId() {
        return id;
    }

    public List<OrderedProduct> getOrderedProduct() {
        return orderedProduct;
    }
}
