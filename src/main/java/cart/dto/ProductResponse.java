package cart.dto;

import cart.entity.ProductEntity;

public class ProductResponse {
    private Long id;
    private String name;
    private String image;
    private Integer price;

    public ProductResponse(Long id, String name, String image, Integer price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public static ProductResponse from(ProductEntity entity) {
        return new ProductResponse(entity.getId(), entity.getName(), entity.getImage(), entity.getPrice());
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
