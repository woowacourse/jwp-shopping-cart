package woowacourse.shoppingcart.cartitem.application.dto;

import woowacourse.shoppingcart.cartitem.ui.dto.CartItemRequest;

public class AddCartItemDto {
    private final Long productId;
    private final Integer quantity;
    private final String email;

    private AddCartItemDto(Long productId, Integer quantity, String email) {
        this.productId = productId;
        this.quantity = quantity;
        this.email = email;
    }

    public static AddCartItemDto from(CartItemRequest cartItemRequest, String email) {
        return new AddCartItemDto(cartItemRequest.getProductId(), cartItemRequest.getQuantity(), email);
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getEmail() {
        return email;
    }
}
