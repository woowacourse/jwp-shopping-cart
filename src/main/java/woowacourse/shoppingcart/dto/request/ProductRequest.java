package woowacourse.shoppingcart.dto.request;

import woowacourse.shoppingcart.domain.Product;

public class ProductRequest {

    private String name;

    private Integer price;

    private String imageUrl;

    private ProductRequest() {
    }

    public ProductRequest(String name, Integer price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product toProduct() {
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
