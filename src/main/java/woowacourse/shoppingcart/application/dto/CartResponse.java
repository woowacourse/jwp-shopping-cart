package woowacourse.shoppingcart.application.dto;

import java.util.List;
import java.util.stream.Collectors;

import woowacourse.shoppingcart.domain.cart.Cart;

public class CartResponse {

    private final List<CartItemResponse> itemResponses;

    private CartResponse() {
        this(null);
    }

    public CartResponse(List<CartItemResponse> itemResponses) {
        this.itemResponses = itemResponses;
    }

    public static CartResponse from(Cart cart) {
        return new CartResponse(cart.getItems().stream()
            .map(CartItemResponse::from)
            .collect(Collectors.toList()));
    }

    public List<CartItemResponse> getItemResponses() {
        return itemResponses;
    }
}
