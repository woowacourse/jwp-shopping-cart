package cart.controller.dto;

import cart.service.dto.CartDto;

public class CartResponse {

    private final Long cartId;
    private final String itemName;
    private final String itemImageUrl;
    private final int itemPrice;

    private CartResponse(Long cartId, String itemName, String itemImageUrl, int itemPrice) {
        this.cartId = cartId;
        this.itemName = itemName;
        this.itemImageUrl = itemImageUrl;
        this.itemPrice = itemPrice;
    }

    public static CartResponse from(CartDto cartDto) {
        return new CartResponse(cartDto.getCartId(), cartDto.getName(), cartDto.getImageUrl(), cartDto.getPrice());
    }

    public Long getCartId() {
        return cartId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemImageUrl() {
        return itemImageUrl;
    }

    public int getItemPrice() {
        return itemPrice;
    }
}
