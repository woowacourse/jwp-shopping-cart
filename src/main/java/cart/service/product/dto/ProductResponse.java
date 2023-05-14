package cart.service.product.dto;

import cart.service.product.domain.Product;

public class ProductResponse {
    private final long id;
    private final String name;
    private final String imageUrl;
    private final int price;

    public ProductResponse(Long id, String name, String imageUrl, int price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getImageUrl(), product.getPrice());
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
