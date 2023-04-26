package cart.service.dto;

import cart.dao.entity.ProductEntity;

public class ProductRequest {

    private String name;
    private int price;
    private String imgUrl;

    public ProductRequest(final String imgUrl, final String name, final int price) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.price = price;
    }

    public ProductEntity toEntity() {
        return new ProductEntity.Builder()
                .name(name)
                .imgUrl(imgUrl)
                .price(price)
                .build();
    }

    public ProductEntity toEntityBy(long id) {
        return new ProductEntity.Builder()
                .name(name)
                .imgUrl(imgUrl)
                .price(price)
                .id(id)
                .build();
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

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
