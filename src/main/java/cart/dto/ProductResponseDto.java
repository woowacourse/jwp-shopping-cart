package cart.dto;

import cart.entity.Product;

public class ProductResponseDto {

    private final Long id;
    private final String name;
    private final String imageUrl;
    private final int price;

    public ProductResponseDto(final Long id, final String name, final String imageUrl, final int price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public ProductResponseDto(final Product product) {
        this(
            product.getId(),
            product.getName(),
            product.getImageUrl(),
            product.getPrice());
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
