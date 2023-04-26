package cart.dto;

import cart.entity.ProductEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ProductDto {

    private final Long id;
    private final String name;
    private final String image;
    private final Integer price;

    private ProductDto(final Long id, final String name, final String image, final Integer price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public static ProductDto from(final ProductCreationRequest request) {
        return new ProductDto(null, request.getName(), request.getImage(), request.getPrice());
    }

    public static ProductDto from(final ProductModificationRequest request) {
        return new ProductDto(request.getId(), request.getName(), request.getImage(), request.getPrice());
    }

    public static ProductDto from(final Long id) {
        return new ProductDto(id, null, null, null);
    }

    public static ProductDto from(final ProductEntity productEntity) {
        return new ProductDto(productEntity.getId(), productEntity.getName(), productEntity.getImage(), productEntity.getPrice());
    }

    public static List<ProductDto> from(final List<ProductEntity> productEntities) {
        return productEntities.stream()
                .map(ProductDto::from)
                .collect(Collectors.toList());
    }

    public static ProductDto of(final String name, final String image, final Integer price) {
        return new ProductDto(null, name, image, price);
    }

    public static ProductDto of(final Long id, final String name, final String image, final Integer price) {
        return new ProductDto(id, name, image, price);
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

    public Integer getPrice() {
        return price;
    }
}
