package woowacourse.shoppingcart.application.dto.request;

import woowacourse.shoppingcart.domain.Product;

public class ProductRequest {

    private String name;
    private Integer price;
    private String imageUrl;

    private ProductRequest() {
    }

    public ProductRequest(final String name, final Integer price, final String imageUrl) {
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
