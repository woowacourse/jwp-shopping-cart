package cart.domain;

import cart.controller.dto.request.product.ProductUpdateRequest;
import cart.dao.ProductEntity;

public class Product {

    private String name;
    private String image;
    private int price;

    public Product(final String name, final String image, final int price) {
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getPrice() {
        return price;
    }

    public ProductEntity toEntity() {
        return new ProductEntity(name, image, price);
    }

    public Product update(ProductUpdateRequest productUpdateRequest) {
        this.name = productUpdateRequest.getName();
        this.image = productUpdateRequest.getImage();
        this.price = productUpdateRequest.getPrice();
        return this;
    }
}
