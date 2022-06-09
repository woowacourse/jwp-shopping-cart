package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.CartItem;

public class CartResponse {

    private final Long id;
    private final String name;
    private final int price;
    private final String imageUrl;
    private final int quantity;

    public CartResponse(CartItem cartItem) {
        this(cartItem.getProduct().getId(),
            cartItem.getProduct().getName(),
            cartItem.getProduct().getPrice(),
            cartItem.getProduct().getImageUrl(),
            cartItem.getQuantity());
    }

    public CartResponse(Long id, String name, int price, String imageUrl, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public Long getId() {
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

    public int getQuantity() {
        return quantity;
    }
}
