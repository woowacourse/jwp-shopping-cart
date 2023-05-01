package cart.controller.dto;

import cart.persistence.entity.Product;
import cart.persistence.entity.ProductCategory;

public class ProductResponse {

    private final Long id;

    private final String name;

    private final String imageUrl;

    private final Integer price;

    private final ProductCategory category;

    public ProductResponse(final Long id, final String name, final String imageUrl, final Integer price, final ProductCategory category) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.category = category;
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

    public Integer getPrice() {
        return price;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public Product toEntity() {
        return new Product(name, imageUrl, price, category);
    }
}
