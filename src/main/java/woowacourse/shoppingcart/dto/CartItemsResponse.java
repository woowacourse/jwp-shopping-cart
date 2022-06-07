package woowacourse.shoppingcart.dto;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.CartItem;

public class CartItemsResponse {
    private List<CartItemResponse> cartItems;

    public CartItemsResponse() {
    }

    private CartItemsResponse(List<CartItemResponse> cartItems) {
        this.cartItems = List.copyOf(cartItems);
    }

    public static CartItemsResponse from(List<CartItem> cartItems) {
        return new CartItemsResponse(cartItems.stream()
                .map(CartItemResponse::from)
                .collect(Collectors.toList()));
    }

    public List<CartItemResponse> getCartItems() {
        return cartItems;
    }
}
