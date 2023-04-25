package cart.dto.response;

import cart.entity.CategoryEntity;
import cart.entity.product.ProductEntity;
import java.util.List;

public class ProductResponseDto {

    private final String name;
    private final String imageUrl;
    private final Integer price;
    private final String description;
    private final List<CategoryResponseDto> categoryResponseDtos;

    private ProductResponseDto(final String name, final String imageUrl, final Integer price, final String description,
        final List<CategoryResponseDto> categoryResponseDtos) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.description = description;
        this.categoryResponseDtos = categoryResponseDtos;
    }

    public static ProductResponseDto of(final ProductEntity productEntity,
        final List<CategoryEntity> categoryEntities) {
        return new ProductResponseDto(
            productEntity.getName(),
            productEntity.getImageUrl(),
            productEntity.getPrice(),
            productEntity.getDescription(),
            CategoryResponseDto.listOf(categoryEntities)
        );
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Integer getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public List<CategoryResponseDto> getCategoryResponseDtos() {
        return categoryResponseDtos;
    }
}
