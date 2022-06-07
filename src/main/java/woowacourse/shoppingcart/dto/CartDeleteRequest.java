package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.application.dto.CartDeleteServiceRequest;
import java.util.List;

public class CartDeleteRequest {

    private List<Long> cartIds;

    public CartDeleteRequest() {
    }

    public CartDeleteRequest(final List<Long> cartIds) {
        this.cartIds = cartIds;
    }

    public CartDeleteServiceRequest toServiceDto() {
        return new CartDeleteServiceRequest(cartIds);
    }

    public List<Long> getCartIds() {
        return cartIds;
    }
}
