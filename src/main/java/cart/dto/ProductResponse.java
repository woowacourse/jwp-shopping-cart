package cart.dto;

import cart.persistence.entity.ProductEntity;

public class ProductResponse {

    private final Long id;
    private final String name;
    private final int price;
    private final String imageUrl;

    public ProductResponse(final Long id, final String name, final int price, final String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static ProductResponse from(final ProductEntity productEntity) {
        return new ProductResponse(productEntity.getId(), productEntity.getName(),
                productEntity.getPrice(), productEntity.getImageUrl());
    }

    public Long getId() {
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
