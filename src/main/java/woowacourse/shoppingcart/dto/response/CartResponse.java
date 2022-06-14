package woowacourse.shoppingcart.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.Cart;

public class CartResponse {

    private List<CartItemResponse> cartItemResponses;

    public CartResponse() {
    }

    public CartResponse(
        List<CartItemResponse> cartItemResponses) {
        this.cartItemResponses = cartItemResponses;
    }

    public static CartResponse of(Cart cart) {
        return new CartResponse(cart.getCartItems().stream()
            .map(CartItemResponse::of)
            .collect(Collectors.toList()));
    }

    public List<CartItemResponse> getCartItemResponses() {
        return cartItemResponses;
    }
}
