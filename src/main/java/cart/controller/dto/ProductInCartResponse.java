package cart.controller.dto;

import cart.service.dto.ProductInCart;

public class ProductInCartResponse {

    private final long id;
    private final String name;
    private final int price;
    private final String imageUrl;

    public ProductInCartResponse(final ProductInCart productInCart) {
        this.id = productInCart.getId();
        this.name = productInCart.getProductName();
        this.price = productInCart.getProductPrice();
        this.imageUrl = productInCart.getProductImageUrl();
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
