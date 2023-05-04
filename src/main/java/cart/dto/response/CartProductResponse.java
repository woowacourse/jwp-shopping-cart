package cart.dto.response;

import cart.domain.CartProduct;

public class CartProductResponse {

    private final Long Id;
    private final String name;
    private final Integer price;
    private final String imageUrl;

    public CartProductResponse(CartProduct cartProduct) {
        this.Id = cartProduct.getId();
        this.name = cartProduct.getProduct().getName();
        this.price = cartProduct.getProduct().getPrice();
        this.imageUrl = cartProduct.getProduct().getImageUrl();
    }

    public Long getId() {
        return Id;
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
