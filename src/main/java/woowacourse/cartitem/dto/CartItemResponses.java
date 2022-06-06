package woowacourse.cartitem.dto;

import java.util.List;
import java.util.stream.Collectors;

import woowacourse.cartitem.domain.CartItem;

public class CartItemResponses {

    private List<CartItemResponse> cartItems;

    private CartItemResponses() {
    }

    public CartItemResponses(final List<CartItemResponse> cartItems) {
        this.cartItems = cartItems;
    }

    public static CartItemResponses from(final List<CartItem> cartItems) {
        final List<CartItemResponse> values = cartItems.stream()
            .map(CartItemResponse::from)
            .collect(Collectors.toList());

        return new CartItemResponses(values);
    }

    public List<CartItemResponse> getCartItems() {
        return cartItems;
    }
}
