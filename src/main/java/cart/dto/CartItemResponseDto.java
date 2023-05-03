package cart.dto;

import cart.entity.CartItem;

public class CartItemResponseDto {

    private final int id;
    private final String name;
    private final int price;
    private final String imageUrl;

    public CartItemResponseDto(CartItem cartItem) {
        this.id = cartItem.getId();
        this.name = cartItem.getProduct().getName();
        this.price = cartItem.getProduct().getPrice();
        this.imageUrl = cartItem.getProduct().getImageUrl();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
