package woowacourse.shoppingcart.dto.cart;

import java.util.List;
import javax.validation.constraints.NotNull;

public class DeleteCartItemRequest {

    @NotNull
    private List<Long> cartIds;

    public DeleteCartItemRequest() {
    }

    public DeleteCartItemRequest(final List<Long> cartIds) {
        this.cartIds = cartIds;
    }

    public List<Long> getCartIds() {
        return cartIds;
    }
}
