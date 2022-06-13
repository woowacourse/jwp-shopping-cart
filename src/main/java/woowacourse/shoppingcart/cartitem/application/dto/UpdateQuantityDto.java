package woowacourse.shoppingcart.cartitem.application.dto;

import woowacourse.shoppingcart.cartitem.ui.dto.CartItemQuantityRequest;

public class UpdateQuantityDto {

    private final Long cartItemId;
    private final Integer quantity;
    private final String email;

    public UpdateQuantityDto(Long cartItemId, Integer quantity, String email) {
        this.cartItemId = cartItemId;
        this.quantity = quantity;
        this.email = email;
    }

    public static UpdateQuantityDto from(CartItemQuantityRequest cartItemQuantityRequest, String email) {
        return new UpdateQuantityDto(cartItemQuantityRequest.getCartItemId(), cartItemQuantityRequest.getQuantity(),
                email);
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getEmail() {
        return email;
    }
}
