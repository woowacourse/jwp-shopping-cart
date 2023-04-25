package cart.domain;

import cart.dto.ProductRequest;

public class Product {

    private final String name;
    private final int price;
    private final String image;

    private Product(String name, int price, String image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public Product of(ProductRequest productRequest) {
//        validateName();
//        validate
        return new Product(productRequest.getName(), productRequest.getPrice(), productRequest.getImage());
    }

}
