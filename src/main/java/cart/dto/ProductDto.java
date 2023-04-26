package cart.dto;

import cart.entity.ProductEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ProductDto {

    private final Long id;
    private final String name;
    private final String image;
    private final int price;

    private ProductDto(final Long id, final String name, final String image, final int price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public static ProductDto from(final ProductEntity productEntity) {
        return new ProductDto(productEntity.getId(), productEntity.getName(), productEntity.getImage(), productEntity.getPrice());
    }

    public static List<ProductDto> from(final List<ProductEntity> productEntities) {
        return productEntities.stream()
                .map(ProductDto::from)
                .collect(Collectors.toList());
    }

    public static ProductDto of(final String name, final String image, final int price) {
        return new ProductDto(null, name, image, price);
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
