package woowacourse.shoppingcart.dto.response;

import java.util.List;
import woowacourse.shoppingcart.domain.CartItem;

public class CartItemsResponse {

    private List<CartItem> cartItems;

    public CartItemsResponse() {
    }

    public CartItemsResponse(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
