package woowacourse.shoppingcart.dto.cartItem;

import java.util.List;

public class CartItemDeleteRequest {
    private List<Long> cartItemIds;

    public CartItemDeleteRequest() {
    }

    public CartItemDeleteRequest(List<Long> cartItemIds) {
        this.cartItemIds = cartItemIds;
    }

    public List<Long> getCartItemIds() {
        return cartItemIds;
    }
}
