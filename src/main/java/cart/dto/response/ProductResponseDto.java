package cart.dto.response;

import cart.dto.ProductCategoryDto;
import cart.entity.category.CategoryEntity;
import java.util.List;
import java.util.stream.Collectors;

public class ProductResponseDto {

    private final Long id;
    private final String name;
    private final String imageUrl;
    private final Integer price;
    private final String description;
    private final List<CategoryEntity> categoryEntities;

    private ProductResponseDto(final Long id, final String name, final String imageUrl, final Integer price,
        final String description,
        final List<CategoryEntity> categoryEntities) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.description = description;
        this.categoryEntities = categoryEntities;
    }

    public static ProductResponseDto of(final ProductCategoryDto productCategoryDto) {
        return new ProductResponseDto(
            productCategoryDto.getId(),
            productCategoryDto.getName(),
            productCategoryDto.getImageUrl(),
            productCategoryDto.getPrice(),
            productCategoryDto.getDescription(),
            productCategoryDto.getCategoryEntities()
        );
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

    public Integer getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getCategoryNames() {
        return categoryEntities.stream()
            .map(CategoryEntity::getName)
            .collect(Collectors.toList());
    }

    public List<CategoryEntity> getCategoryEntities() {
        return categoryEntities;
    }
}
