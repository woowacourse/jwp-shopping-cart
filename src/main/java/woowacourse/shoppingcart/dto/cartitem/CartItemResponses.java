package woowacourse.shoppingcart.dto.cartitem;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.dto.cartitem.CartItemResponse.CartItemResponseNested;

public class CartItemResponses {

    private List<CartItemResponseNested> cartItems;

    private CartItemResponses() {
    }

    public CartItemResponses(final List<CartItemResponseNested> cartItems) {
        this.cartItems = cartItems;
    }

    public static CartItemResponses from(final List<CartItem> cartItems) {
        return new CartItemResponses(cartItems.stream()
                .map(CartItemResponseNested::from)
                .collect(Collectors.toList()));
    }

    public List<CartItemResponseNested> getCartItems() {
        return cartItems;
    }
}
