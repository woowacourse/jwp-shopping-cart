package woowacourse.shoppingcart.cart.dto;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.cart.domain.Cart;

public class CartItemsResponse {

    private final List<CartItemResponse> cartList;

    private CartItemsResponse(final List<CartItemResponse> cartList) {
        this.cartList = cartList;
    }

    public static CartItemsResponse from(final Cart cart) {
        final List<CartItemResponse> responses = cart
                .getValues()
                .stream()
                .map(CartItemResponse::new)
                .distinct()
                .collect(Collectors.toList());
        return new CartItemsResponse(responses);
    }

    public List<CartItemResponse> getCartList() {
        return cartList;
    }
}
