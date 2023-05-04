package cart.controller.dto.response;

import cart.entity.CartEntity;
import cart.entity.ProductEntity;

public class CartItemResponse {

    private final Long id;
    private final String name;
    private final Integer price;
    private final String imageUrl;

    private CartItemResponse(Long id, String name, Integer price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static CartItemResponse from(ProductEntity product) {
        return new CartItemResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }


}
