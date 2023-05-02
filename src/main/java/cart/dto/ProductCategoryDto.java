package cart.dto;

import cart.entity.CategoryEntity;
import cart.entity.product.ProductEntity;
import java.util.List;
import java.util.stream.Collectors;

public class ProductCategoryDto {

    private final Long id;
    private final String name;
    private final String imageUrl;
    private final Integer price;
    private final String description;
    private final List<CategoryEntity> categoryEntities;

    private ProductCategoryDto(final Long id, final String name, final String imageUrl, final Integer price,
        final String description,
        final List<CategoryEntity> categoryEntities) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.description = description;
        this.categoryEntities = categoryEntities;
    }

    public static ProductCategoryDto of(final ProductEntity productEntity,
        final List<CategoryEntity> categoryEntities) {
        return new ProductCategoryDto(
            productEntity.getId(),
            productEntity.getName(),
            productEntity.getImageUrl(),
            productEntity.getPrice(),
            productEntity.getDescription(),
            categoryEntities
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
