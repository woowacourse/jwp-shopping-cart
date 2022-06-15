package woowacourse.shoppingcart.order.application.dto.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderRequest {

    private List<Long> productIds;

    public OrderRequest() {
    }

    public OrderRequest(final List<Long> productIds) {
        validateNonNull(productIds);
        this.productIds = productIds;
    }

    public OrderRequest(final Long... productIds) {
        this(new ArrayList<>(List.of(productIds)));
    }

    private void validateNonNull(final List<Long> productIds) {
        productIds.forEach(Objects::requireNonNull);
    }

    public List<Long> getProductIds() {
        return productIds;
    }
}
