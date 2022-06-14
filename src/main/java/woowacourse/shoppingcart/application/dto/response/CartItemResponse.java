package woowacourse.shoppingcart.application.dto.response;

import woowacourse.shoppingcart.domain.CartItem;

import java.util.List;
import java.util.stream.Collectors;

public class CartItemResponse {

    private final Long id;
    private final int quantity;

    private CartItemResponse(final Long id, final int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public static CartItemResponse from(final CartItem cartItem) {
        return new CartItemResponse(cartItem.getId(), cartItem.getQuantity());
    }

    public static List<CartItemResponse> from(final List<CartItem> cartItems) {
        return cartItems.stream()
                .map(CartItemResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }
}
