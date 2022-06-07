package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Product;

public class ProductRequest {

    private Long id;
    private String name;
    private int price;
    private String imageUrl;
    private int stock;

    private ProductRequest() {
    }

    public ProductRequest(Long id, String name, int price, String imageUrl, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.stock = stock;
    }

    public Product toProduct() {
        return new Product(name, price, imageUrl, stock);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getStock() {
        return stock;
    }
}
