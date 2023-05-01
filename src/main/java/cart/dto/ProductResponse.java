package cart.dto;

import cart.entity.ProductEntity;

public class ProductResponse {
    private final Long id;
    private final String name;
    private final String image;
    private final Integer price;

    public ProductResponse(Long id, String name, String image, Integer price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public static ProductResponse from(ProductEntity entity) {
        return new ProductResponse(entity.getId(), entity.getName(), entity.getImageUrl(), entity.getPrice());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Integer getPrice() {
        return price;
    }
}
