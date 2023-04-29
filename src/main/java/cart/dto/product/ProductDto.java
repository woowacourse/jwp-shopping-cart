package cart.dto.product;

import cart.entity.ProductEntity;

public class ProductDto {
    private final Long id;
    private final String name;
    private final int price;
    private final String imageUrl;

    public ProductDto(Long id, String name, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static ProductDto fromEntity(ProductEntity entity) {
        return new ProductDto(entity.getId(), entity.getName(), entity.getPrice(), entity.getImageUrl());
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
