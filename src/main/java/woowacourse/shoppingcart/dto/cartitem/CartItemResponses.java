package woowacourse.shoppingcart.dto.cartitem;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.CartItem;

public class CartItemResponses {

    private List<CartItemResponse> cartItems;

    private CartItemResponses() {
    }

    public CartItemResponses(final List<CartItemResponse> cartItems) {
        this.cartItems = cartItems;
    }

    public static CartItemResponses from(final List<CartItem> cartItems) {
        return new CartItemResponses(cartItems.stream()
                .map(CartItemResponse::from)
                .collect(Collectors.toList()));
    }

    public List<CartItemResponse> getCartItems() {
        return cartItems;
    }
}
