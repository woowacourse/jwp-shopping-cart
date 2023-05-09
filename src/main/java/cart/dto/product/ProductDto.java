package cart.dto.product;

import cart.entity.ProductEntity;

public class ProductDto {
    private final Long productId;
    private final String name;
    private final int price;
    private final String imageUrl;

    public ProductDto(Long productId, String name, int price, String imageUrl) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static ProductDto fromEntity(ProductEntity entity) {
        return new ProductDto(entity.getId(), entity.getName(), entity.getPrice(), entity.getImageUrl());
    }

    public Long getProductId() {
        return productId;
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
