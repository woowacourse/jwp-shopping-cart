package cart.dto;

import cart.domain.product.Product;

public class CartProductResponse {

    private final long id;
    private final String name;
    private final int price;
    private final String imageUrl;

    public CartProductResponse(final long id, final String name, final int price, final String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static CartProductResponse of(final Product product) {
        return new CartProductResponse(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
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
