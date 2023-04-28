package cart.domain.product.service.dto;

import cart.domain.product.Product;

public class ProductDto {
    private final Long id;
    private final String name;
    private final int price;
    private final String category;
    private final String imageUrl;

    public ProductDto(Long id, String name, int price, String category, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public ProductDto(Product product) {
        this(
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
