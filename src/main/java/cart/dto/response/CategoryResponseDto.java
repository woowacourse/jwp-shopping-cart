package cart.dto.response;

import cart.entity.CategoryEntity;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryResponseDto {

    private final Long id;
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
