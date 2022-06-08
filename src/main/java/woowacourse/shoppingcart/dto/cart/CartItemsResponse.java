package woowacourse.shoppingcart.dto.cart;

import woowacourse.shoppingcart.domain.Cart;

import java.util.List;
import java.util.stream.Collectors;

public class CartItemsResponse {
    private List<CartItemResponse> cartList;

    public CartItemsResponse() {
    }

    public CartItemsResponse(List<CartItemResponse> cartList) {
        this.cartList = cartList;
    }

    public static CartItemsResponse from(List<Cart> carts) {
        List<CartItemResponse> cartItemResponses = carts.stream()
                .map(CartItemResponse::from)
                .collect(Collectors.toList());

        return new CartItemsResponse(cartItemResponses);
    }

    public List<CartItemResponse> getCartList() {
        return cartList;
    }
}
