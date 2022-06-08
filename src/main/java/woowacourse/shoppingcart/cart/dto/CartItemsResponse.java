package woowacourse.shoppingcart.cart.dto;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.cart.domain.Cart;

public class CartItemsResponse {

    private final List<CartItemResponse> cartList;

    private CartItemsResponse(List<CartItemResponse> cartList) {
        this.cartList = cartList;
    }

    public static CartItemsResponse from(List<Cart> carts) {
        final List<CartItemResponse> responses = carts.stream()
                .map(CartItemResponse::new)
                .distinct()
                .collect(Collectors.toList());
        return new CartItemsResponse(responses);
    }

    public List<CartItemResponse> getCartList() {
        return cartList;
    }
}
