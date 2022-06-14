package woowacourse.shoppingcart.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.CartItem;

public class CartResponse {

    private List<CartItemResponse> cartItems;

    private CartResponse() {
    }

    private CartResponse(List<CartItemResponse> cartItems) {
        this.cartItems = cartItems;
    }

    public static CartResponse from(List<CartItem> cartItems) {
        List<CartItemResponse> cartItemResponse = cartItems.stream()
                .map(CartItemResponse::from)
                .collect(Collectors.toList());
        return new CartResponse(cartItemResponse);
    }

    public static CartResponse from(Cart cart) {
        List<CartItemResponse> cartItemRespons = cart.getValue().stream()
                .map(CartItemResponse::from)
                .collect(Collectors.toList());
        return new CartResponse(cartItemRespons);
    }

    public List<CartItemResponse> getCartItems() {
        return cartItems;
    }
}
