package cart.controller.dto;

import cart.domain.Product;

public class ProductDto {

    private final Long id;
    private final String name;
    private final String imageUrl;
    private final int price;

    public ProductDto(final Long id, final String name, final String imageUrl, final int price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public static ProductDto from(final Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getImageUrl(), product.getPrice());
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
