package woowacourse.shoppingcart.application.dto;

import woowacourse.shoppingcart.domain.Product;

public class ProductSaveServiceRequest {

    private final String name;
    private final Integer price;
    private final String imageUrl;

    public ProductSaveServiceRequest(final String name, final Integer price, final String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product toEntity() {
        return new Product(name, price, imageUrl);
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
