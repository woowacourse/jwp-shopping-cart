package cart.domain;

import cart.dto.ProductRequest;

public class Product {
    private final String name;
    private final String image;
    private final Integer price;

    public Product(String name, String image, Integer price) {
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public static Product from(ProductRequest productRequest) {
        return new Product(productRequest.getName(), productRequest.getImage(), productRequest.getPrice());
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }
}
