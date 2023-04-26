package cart.dto;

import cart.repository.entity.ProductEntity;

public class ProductDto {

    private Long id;
    private String name;
    private String imageUrl;
    private int price;

    public ProductDto() {
    }

    public ProductDto(final Long id, final String name, final String imageUrl, final int price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public ProductDto(final String name, final String imageUrl, final int price) {
        this(null, name, imageUrl, price);
    }

    public static ProductDto from(final ProductEntity productEntity) {
        return new ProductDto(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getImageUrl(),
                productEntity.getPrice());
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
