package woowacourse.shoppingcart.dto.cartitem;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.dto.cartitem.CartItemResponse.CartItemResponseNested;

public class CartItemsResponse {

    private List<CartItemResponseNested> cartItems;

    private CartItemsResponse() {
    }

    public CartItemsResponse(final List<CartItemResponseNested> cartItems) {
        this.cartItems = cartItems;
    }

    public static CartItemsResponse from(final List<CartItem> cartItems) {
        return new CartItemsResponse(cartItems.stream()
                .map(CartItemResponseNested::from)
                .collect(Collectors.toList()));
    }

    public List<CartItemResponseNested> getCartItems() {
        return cartItems;
    }
}
