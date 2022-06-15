package woowacourse.shoppingcart.dto.request;

import woowacourse.shoppingcart.domain.Product;

public class ProductRequest {

    private String name;
    private int price;
    private int stock;
    private String imageUrl;

    private ProductRequest() {
    }

    public ProductRequest(String name, int price, int stock, String imageUrl) {
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

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
