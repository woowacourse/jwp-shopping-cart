package cart.service.dto;

import cart.domain.Cart;
import cart.domain.item.Item;

public class CartDto {

    private final Long cartId;
    private final Long itemId;
    private final String name;
    private final String imageUrl;
    private final int price;

    public CartDto(final Long cartId, final Long itemId, final String name, final String imageUrl, final int price) {
        this.cartId = cartId;
        this.itemId = itemId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public static CartDto from(Cart cart) {
        Item item = cart.getItem();

        return new CartDto(cart.getId(), item.getId(), item.getName(), item.getImageUrl(), item.getPrice());
    }

    public Long getCartId() {
        return cartId;
    }

    public Long getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPrice() {
        return price;
    }
}
