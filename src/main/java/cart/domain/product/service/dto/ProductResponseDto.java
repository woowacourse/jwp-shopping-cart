package cart.domain.product.service.dto;

import cart.domain.product.Product;

public class ProductResponseDto {
    private final Long id;
    private final String name;
    private final int price;
    private final String category;
    private final String imageUrl;

    public ProductResponseDto(final Long id, final String name, final int price, final String category,
                              final String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public static ProductResponseDto from(final Product product) {
        return new ProductResponseDto(
                product.getProductId(),
                product.getName(),
                product.getPrice().intValue(),
                product.getCategory().name(),
                product.getImageUrl()
        );
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

    public String getCategory() {
        return category;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
