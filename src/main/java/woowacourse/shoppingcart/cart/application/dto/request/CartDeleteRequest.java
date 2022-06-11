package woowacourse.shoppingcart.cart.application.dto.request;

import java.util.ArrayList;
import java.util.List;

public class CartDeleteRequest {

    private List<Long> productIds;

    public CartDeleteRequest() {
    }

    public CartDeleteRequest(final List<Long> productIds) {
        this.productIds = productIds;
    }

    public CartDeleteRequest(final Long... productIds) {
        this.productIds = new ArrayList<>(List.of(productIds));
    }

    public List<Long> getProductIds() {
        return productIds;
    }
}
