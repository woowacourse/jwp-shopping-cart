package woowacourse.shoppingcart.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.Cart;

public class CartResponses {

    private List<CartResponse> cartItems;

    private CartResponses() {
    }

    private CartResponses(List<CartResponse> cartItems) {
        this.cartItems = cartItems;
    }

    public static CartResponses from(List<Cart> carts) {
        List<CartResponse> cartResponses = carts.stream()
                .map(CartResponse::from)
                .collect(Collectors.toList());
        return new CartResponses(cartResponses);
    }

    public List<CartResponse> getCartItems() {
        return cartItems;
    }
}
