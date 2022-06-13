package woowacourse.shoppingcart.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.CartItem;

public class CartResponses {

    private List<CartResponse> cartItems;

    private CartResponses() {
    }

    private CartResponses(List<CartResponse> cartItems) {
        this.cartItems = cartItems;
    }

    public static CartResponses from(List<CartItem> cartItems) {
        List<CartResponse> cartResponses = cartItems.stream()
                .map(CartResponse::from)
                .collect(Collectors.toList());
        return new CartResponses(cartResponses);
    }

    public static CartResponses from(Cart cart) {
        List<CartResponse> cartResponses = cart.getValue().stream()
                .map(CartResponse::from)
                .collect(Collectors.toList());
        return new CartResponses(cartResponses);
    }

    public List<CartResponse> getCartItems() {
        return cartItems;
    }
}
