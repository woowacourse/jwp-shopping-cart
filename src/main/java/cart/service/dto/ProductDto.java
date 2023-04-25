package cart.service.dto;

import cart.dao.entity.ProductEntity;

public class ProductDto {

    private final long id;
    private final String imgUrl;
    private final String name;
    private final int price;

    public ProductDto(final long id, final String imgUrl, final String name, final int price) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.name = name;
        this.price = price;
    }

    public static ProductDto fromEntity(final ProductEntity productEntity) {
        return new ProductDto(productEntity.getId(), productEntity.getImgUrl(), productEntity.getName(),
                productEntity.getPrice());
    }

    public long getId() {
        return id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
