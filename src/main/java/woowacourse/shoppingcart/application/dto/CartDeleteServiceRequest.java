package woowacourse.shoppingcart.application.dto;

import java.util.List;

public class CartDeleteServiceRequest {

    private final List<Long> cartIds;

    public CartDeleteServiceRequest(final List<Long> cartIds) {
        this.cartIds = cartIds;
    }

    public List<Long> getCartIds() {
        return cartIds;
    }
}
