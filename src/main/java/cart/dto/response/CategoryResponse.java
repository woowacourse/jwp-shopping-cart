package cart.dto.response;

import cart.entity.CategoryEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.stream.Collectors;

public final class CategoryResponse {

    @Schema(description = "카테고리 ID")
    private final Long id;
    @Schema(description = "카테고리명")
    private final String name;

    private CategoryResponse(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public static List<CategoryResponse> listOf(final List<CategoryEntity> categoryEntities) {
        return categoryEntities.stream()
                .map(CategoryResponse::from)
                .collect(Collectors.toList());
    }

    public static CategoryResponse from(final CategoryEntity categoryEntity) {
        return new CategoryResponse(categoryEntity.getId(), categoryEntity.getName());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
