package woowacourse.shoppingcart.dto.request;

import woowacourse.shoppingcart.domain.Product;

public class ProductRequest {

    private String name;
    private Integer price;
    private Integer stock;
    private String imageUrl;

    private ProductRequest() {
    }

    public ProductRequest(String name, Integer price, Integer stock, String imageUrl) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
    }

    public Product toProduct() {
        return new Product(name, price, stock, imageUrl);
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
