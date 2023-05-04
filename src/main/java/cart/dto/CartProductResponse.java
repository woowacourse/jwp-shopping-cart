package cart.dto;

import cart.persistence.entity.ProductEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CartProductResponse {
    @JsonProperty("id")
    private final Long cartId;
    private final String name;
    private final int price;
    private final String imageUrl;

    public CartProductResponse(final Long cartId, final String name, final int price, final String imageUrl) {
        this.cartId = cartId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static CartProductResponse from(final ProductEntity productEntity) {
        return new CartProductResponse(productEntity.getId(), productEntity.getName(),
                productEntity.getPrice(), productEntity.getImageUrl());
    }

    public Long getCartId() {
        return cartId;
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
