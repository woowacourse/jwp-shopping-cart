package woowacourse.cartitem.dto;

import java.util.List;
import java.util.stream.Collectors;

import woowacourse.cartitem.domain.CartItem;
import woowacourse.cartitem.dto.CartItemResponse.InnerCartItemResponse;

public class CartItemResponses {

    private List<InnerCartItemResponse> cartItems;

    private CartItemResponses() {
    }

    public CartItemResponses(final List<InnerCartItemResponse> cartItems) {
        this.cartItems = cartItems;
    }

    public static CartItemResponses from(final List<CartItem> cartItems) {
        final List<InnerCartItemResponse> cartItemResponses = cartItems.stream()
            .map(InnerCartItemResponse::from)
            .collect(Collectors.toList());

        return new CartItemResponses(cartItemResponses);
    }

    public List<InnerCartItemResponse> getCartItems() {
        return cartItems;
    }
}
