package cart.dto;

import cart.entity.Product;

public final class ProductResponse {
    private final Long id;
    private final String name;
    private final String image;
    private final int price;

    public ProductResponse(Long id, String name, String image, int price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getImage(), product.getPrice());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getPrice() {
        return price;
    }
}
