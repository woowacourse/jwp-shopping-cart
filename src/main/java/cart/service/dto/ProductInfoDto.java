package cart.service.dto;

import cart.dao.entity.ProductEntity;

public class ProductInfoDto {

    private final long id;
    private final String imgUrl;
    private final String name;
    private final int price;

    public ProductInfoDto(final long id, final String imgUrl, final String name, final int price) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.name = name;
        this.price = price;
    }

    public static ProductInfoDto fromEntity(final ProductEntity productEntity) {
        return new ProductInfoDto(productEntity.getId(), productEntity.getImgUrl(), productEntity.getName(),
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
