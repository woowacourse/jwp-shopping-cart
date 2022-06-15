package woowacourse.shoppingcart.cart.application.dto.request;

import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;

public class CartDeleteRequest {

    @NotNull
    private List<Long> productIds;

    public CartDeleteRequest() {
    }

    public CartDeleteRequest(final List<Long> productIds) {
        productIds.forEach(Objects::requireNonNull);
        this.productIds = productIds;
    }

    public List<Long> getProductIds() {
        return productIds;
    }
}
