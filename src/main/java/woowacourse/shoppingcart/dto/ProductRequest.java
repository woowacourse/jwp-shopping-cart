package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Product;

public class ProductRequest {

    private String name;
    private String thumbnail;
    private Integer price;

    public ProductRequest() {
    }

    public ProductRequest(String name, String thumbnail, Integer price) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.price = price;
    }

    public Product createProduct() {
        return new Product(name, thumbnail, price);
    }

    public String getName() {
        return name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public Integer getPrice() {
        return price;
    }
}
