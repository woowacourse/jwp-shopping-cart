package cart.service.dto;

import cart.domain.product.Product;

public class ProductDto {

    private final Long id;
    private final String name;
    private final Long price;
    private final String imageUrl;

    public ProductDto(Long id, String name, Long price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }


    public static ProductDto from(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl()
        );
    }

    public Product toDomain() {
        return Product.create(this.id, this.name, this.price, this.imageUrl);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
