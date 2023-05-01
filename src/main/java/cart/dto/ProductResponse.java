package cart.dto;

import cart.entity.ProductEntity;

public class ProductResponse {

    private final long id;
    private final String imgUrl;
    private final String name;
    private final int price;

    public ProductResponse(final long id, final String imgUrl, final String name, final int price) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.name = name;
        this.price = price;
    }

    public static ProductResponse fromEntity(final ProductEntity productEntity) {
        return new ProductResponse(productEntity.getProductId(), productEntity.getImgUrl(), productEntity.getName(),
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
