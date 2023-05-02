package cart.dto;

import cart.entity.ProductEntity;

public class ProductDto {

    private final Long id;
    private final String name;
    private final String imgUrl;
    private final int price;

    private ProductDto(Long id, String name, String imgUrl, int price) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
    }

    public static ProductDto fromEntity(ProductEntity entity) {
        return new ProductDto(entity.getId(), entity.getName(), entity.getImgUrl(), entity.getPrice());
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getPrice() {
        return price;
    }

    public Long getId() {
        return id;
    }
}
