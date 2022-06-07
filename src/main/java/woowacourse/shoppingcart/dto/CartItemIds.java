package woowacourse.shoppingcart.dto;

import java.util.List;

public class CartItemIds {

    private List<Long> cartItemIds;

    public CartItemIds() {
    }

    public CartItemIds(List<Long> cartItemIds) {
        this.cartItemIds = cartItemIds;
    }

    public List<Long> getCartItemIds() {
        return cartItemIds;
    }
}
