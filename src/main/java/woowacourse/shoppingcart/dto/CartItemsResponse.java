package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.CartItem;

import java.util.List;

public class CartItemsResponse {

    private final List<CartItem> cartItemItems;

    public CartItemsResponse(List<CartItem> cartItemItems) {
        this.cartItemItems = cartItemItems;
    }

    public List<CartItem> getCartItems() {
        return cartItemItems;
    }
}
