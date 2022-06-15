package woowacourse.shoppingcart.ui.cart.dto.request;

import java.util.List;

public class CartDeleteRequest {

    private List<Long> cartIds;

    public CartDeleteRequest() {
    }

    public CartDeleteRequest(final List<Long> cartIds) {
        this.cartIds = cartIds;
    }

    public List<Long> getCartIds() {
        return cartIds;
    }
}
