package woowacourse.shoppingcart.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.CartItem;

public class CartItemsResponse {
    private List<CartItemResponse> cartItems;

    public CartItemsResponse() {
    }

    private CartItemsResponse(List<CartItemResponse> cartItems) {
        this.cartItems = List.copyOf(cartItems);
    }

    public static CartItemsResponse from(Cart cart) {
        return new CartItemsResponse(cart.getItems().stream()
                .map(CartItemResponse::from)
                .collect(Collectors.toList()));
    }

    public List<CartItemResponse> getCartItems() {
        return cartItems;
    }
}
