package cart.domain;

import cart.controller.dto.ProductRequest;

public class Product {

    private final String name;
    private final Integer price;
    private final String imageUrl;

    public static Product from(ProductRequest request) {
        return new Product(request.getName(), request.getPrice(), request.getImageUrl());
    }

    public Product(String name, Integer price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
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
