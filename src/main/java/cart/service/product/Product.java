package cart.service.product;

import cart.service.product.dto.ProductRequest;

public class Product {

    private final Long id;
    private final String name;
    private final String imageUrl;
    private final int price;

    public Product(Long id, String name, String imageUrl, int price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public Product(String name, String imageUrl, int price) {
        this(null, name, imageUrl, price);
    }

    public Product replaceProduct(ProductRequest productRequest, Long id) {
        return new Product(id, productRequest.getName(), productRequest.getImageUrl(), productRequest.getPrice());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPrice() {
        return price;
    }
}
