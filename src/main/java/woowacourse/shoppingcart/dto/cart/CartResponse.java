package woowacourse.shoppingcart.dto.cart;

import java.util.List;
import java.util.stream.Collectors;

import woowacourse.shoppingcart.domain.cart.Cart;

public class CartResponse {

    private final List<CartItemResponse> cartItemResponses;

    private CartResponse(List<CartItemResponse> cartItemResponses) {
        this.cartItemResponses = cartItemResponses;
    }

    public static CartResponse from(Cart cart) {
        List<CartItemResponse> cartItemResponses = convertCartToCartItemResponses(cart);
        return new CartResponse(cartItemResponses);
    }

    private static List<CartItemResponse> convertCartToCartItemResponses(Cart cart) {
        return cart.getCartItems()
            .stream()
            .map(CartItemResponse::from)
            .collect(Collectors.toList());
    }

    public List<CartItemResponse> getCartItemResponses() {
        return cartItemResponses;
    }
}
