package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.CartItem;

public class CartItemQuantityResponse {

    private Long id;
    private int quantity;

    public CartItemQuantityResponse() {
    }

    public CartItemQuantityResponse(final Long id, final int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public static CartItemQuantityResponse of(final CartItem cartItem) {
        return new CartItemQuantityResponse(cartItem.getId(), cartItem.getQuantity());
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }
}
