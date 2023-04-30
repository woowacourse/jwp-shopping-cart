package cart.controller.dto;

import cart.domain.CartProduct;
import cart.domain.Product;

public class CartProductResponse {

    private Long id;
    private String name;
    private String imageUrl;
    private int price;

    public CartProductResponse(final Long id, final String name, final String imageUrl, final int price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public static CartProductResponse of(final CartProduct cartProduct,
                                         final Product product) {
        return new CartProductResponse(
                cartProduct.getId(),
                product.getName(),
                product.getImageUrl(),
                product.getPrice()
        );
    }

    public Long getId() {
        return id;
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
