package cart.dto.response;

import cart.entity.CategoryEntity;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.stream.Collectors;

public final class CategoryResponseDto {

    @Schema(description = "카테고리 ID")
    private final Long id;
    @Schema(description = "카테고리명")
    private final String name;

    private CategoryResponseDto(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public static List<CategoryResponseDto> listOf(final List<CategoryEntity> categoryEntities) {
        return categoryEntities.stream()
                .map(CategoryResponseDto::from)
                .collect(Collectors.toList());
    }

    public static CategoryResponseDto from(final CategoryEntity categoryEntity) {
        return new CategoryResponseDto(categoryEntity.getId(), categoryEntity.getName());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
