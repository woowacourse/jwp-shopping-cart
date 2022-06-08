package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Product;

public class ProductRequest {

    private String name;
    private String imageUrl;
    private Integer price;

    public ProductRequest() {
    }

    public ProductRequest(String name, String imageUrl, Integer price) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public Product createProduct() {
        return new Product(name, imageUrl, price);
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Integer getPrice() {
        return price;
    }
}
