package cart.dto.response;

import cart.persistnece.entity.CartProduct;

public class CartProductResponse {

    private final Long id;
    private final String name;
    private final Integer price;
    private final String imageUrl;

    public CartProductResponse(CartProduct cartProduct) {
        this.id = cartProduct.getId();
        this.name = cartProduct.getProduct().getName();
        this.price = cartProduct.getProduct().getPrice();
        this.imageUrl = cartProduct.getProduct().getImageUrl();
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
