package cart.dto;

import cart.entity.ProductEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ProductDto {

    private final String name;
    private final String image;
    private final int price;

    private ProductDto(final String name, final String image, final int price) {
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public static ProductDto of(final String name, final String image, final int price) {
        return new ProductDto(name, image, price);
    }

    public static ProductDto from(ProductEntity productEntity) {
        return new ProductDto(productEntity.getName(), productEntity.getImage(), productEntity.getPrice());
    }

    public static List<ProductDto> from(List<ProductEntity> productEntities) {
        return productEntities.stream()
                .map(ProductDto::from)
                .collect(Collectors.toList());
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
