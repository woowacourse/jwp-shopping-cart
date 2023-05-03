package cart.dto.response;

import cart.entity.CategoryEntity;
import cart.entity.product.ProductEntity;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.stream.Collectors;

public final class ProductResponseDto {

    @Schema(description = "상품 ID")
    private final Long id;
    @Schema(description = "상품명")
    private final String name;
    @Schema(description = "상품 이미지 URL")
    private final String imageUrl;
    @Schema(description = "상품 가격")
    private final Integer price;
    @Schema(description = "상품 설명")
    private final String description;
    @Schema(description = "상품 카테고리 목록")
    private final List<CategoryResponseDto> categoryResponseDtos;

    private ProductResponseDto(
            final Long id,
            final String name,
            final String imageUrl,
            final Integer price,
            final String description,
            final List<CategoryResponseDto> categoryResponseDtos
    ) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.description = description;
        this.categoryResponseDtos = categoryResponseDtos;
    }

    public static ProductResponseDto of(final ProductEntity productEntity, final List<CategoryEntity> categoryEntities) {
        return new ProductResponseDto(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getImageUrl(),
                productEntity.getPrice(),
                productEntity.getDescription(),
                CategoryResponseDto.listOf(categoryEntities)
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
        return categoryResponseDtos.stream()
                .map(CategoryResponseDto::getName)
                .collect(Collectors.toList());
    }

    public List<CategoryResponseDto> getCategoryResponseDtos() {
        return categoryResponseDtos;
    }
}
