package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.CartItem;

import java.util.List;
import java.util.stream.Collectors;

public class CartItemsResponse {

    private final List<CartItemResponse> cartItemItems;

    private CartItemsResponse(List<CartItemResponse> cartItemItems) {
        this.cartItemItems = cartItemItems;
    }

    public static CartItemsResponse from(List<CartItem> cartItems) {
        return new CartItemsResponse(cartItems.stream()
                .map(CartItemResponse::from)
                .collect(Collectors.toList()));
    }

    public List<CartItemResponse> getCartItems() {
        return cartItemItems;
    }
}
