package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Product;

public class ProductResponse {

    private final Long id;
    private final String thumbnail;
    private final String name;
    private final Integer price;
    private final Boolean isStored;

    private ProductResponse(Long id, String thumbnail, String name, Integer price, Boolean isStored) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.name = name;
        this.price = price;
        this.isStored = isStored;
    }

    public static ProductResponse from(Product product, Boolean isStored) {
        return new ProductResponse(
                product.getId(), product.getThumbnail(), product.getName(), product.getPrice(), isStored);
    }

    public Long getId() {
        return id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Boolean getStored() {
        return isStored;
    }
}
