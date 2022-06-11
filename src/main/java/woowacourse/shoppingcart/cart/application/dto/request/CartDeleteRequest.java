package woowacourse.shoppingcart.cart.application.dto.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CartDeleteRequest {

    private List<Long> productIds;

    public CartDeleteRequest() {
    }

    public CartDeleteRequest(final List<Long> productIds) {
        validateNonNull(productIds);
        this.productIds = productIds;
    }

    public CartDeleteRequest(final Long... productIds) {
        this.productIds = new ArrayList<>(List.of(productIds));
    }

    private void validateNonNull(final List<Long> productIds) {
        productIds.forEach(Objects::requireNonNull);
    }

    public List<Long> getProductIds() {
        return productIds;
    }
}
