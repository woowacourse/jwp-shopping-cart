package woowacourse.shoppingcart.order.application.dto.request;

import java.util.ArrayList;
import java.util.List;

public class OrderRequest {

    private List<Long> productIds;

    public OrderRequest() {
    }

    public OrderRequest(final List<Long> productIds) {
        this.productIds = productIds;
    }

    public OrderRequest(final Long... productIds) {
        this(new ArrayList<>(List.of(productIds)));
    }

    public List<Long> getProductIds() {
        return productIds;
    }
}
