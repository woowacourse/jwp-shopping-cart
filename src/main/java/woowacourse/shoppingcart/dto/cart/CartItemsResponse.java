package woowacourse.shoppingcart.dto.cart;

import woowacourse.shoppingcart.domain.CartItem;

import java.util.List;
import java.util.stream.Collectors;

public class CartItemsResponse {
    private List<CartItemResponse> cartList;

    public CartItemsResponse() {
    }

    public CartItemsResponse(List<CartItemResponse> cartList) {
        this.cartList = cartList;
    }

    public static CartItemsResponse from(List<CartItem> cartItems) {
        List<CartItemResponse> cartItemResponses = cartItems.stream()
                .map(CartItemResponse::from)
                .collect(Collectors.toList());

        return new CartItemsResponse(cartItemResponses);
    }

    public List<CartItemResponse> getCartList() {
        return cartList;
    }
}
