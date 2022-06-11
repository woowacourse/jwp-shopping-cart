package woowacourse.shoppingcart.dto.cart;

import java.util.List;
import java.util.stream.Collectors;

import woowacourse.shoppingcart.domain.cart.Cart;

public class CartResponse {

    private final List<CartItemResponse> value;

    private CartResponse(List<CartItemResponse> cartItemResponses) {
        this.value = cartItemResponses;
    }

    public static CartResponse from(Cart cart) {
        List<CartItemResponse> cartItemResponses = convertCartToCartItemResponses(cart);
        return new CartResponse(cartItemResponses);
    }

    private static List<CartItemResponse> convertCartToCartItemResponses(Cart cart) {
        return cart.getValue()
            .stream()
            .map(CartItemResponse::from)
            .collect(Collectors.toList());
    }

    public List<CartItemResponse> getValue() {
        return value;
    }
}
