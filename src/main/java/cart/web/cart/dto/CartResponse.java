package cart.web.cart.dto;

import cart.persistence.CartProduct;

public class CartResponse {

    private final long id;
    private final String name;
    private final int price;
    private final String imageUrl;

    public CartResponse(final long id, final String name, final int price, final String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static CartResponse from(final CartProduct cartProduct) {
        return new CartResponse(cartProduct.getCartId(), cartProduct.getName(), cartProduct.getPrice(), cartProduct.getImageUrl());
    }

    public long getId() {
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
