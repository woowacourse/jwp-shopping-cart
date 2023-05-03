package cart.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public final class ProductRequestDto {

    @Schema(description = "상품명")
    @NotBlank(message = "상품명은 비어있을 수 없습니다.")
    @Size(min = 1, max = 50, message = "상품명은 {min}이상, {max}이하여야 합니다.")
    private final String name;

    @Schema(description = "상품 이미지 URL")
    @NotBlank(message = "이미지URL은 비어있을 수 없습니다.")
    private final String imageUrl;

    @Schema(description = "상품 가격")
    @NotNull(message = "가격은 비어있을 수 없습니다.")
    @Range(min = 0, max = Integer.MAX_VALUE, message = "가격은 {min}원 이상 {max}원 이하여야 합니다.")
    private final Integer price;

    @Schema(description = "상품 설명")
    @Size(min = 1, max = 255, message = "상품설명의 길이는 {min}이상, {max}이하여야 합니다.")
    private final String description;

    @Schema(description = "상품 카테고리 ID 목록")
    @NotNull(message = "카테고리를 선택해야 합니다.")
    private final List<Long> categoryIds;

    public ProductRequestDto(
            final String name,
            final String imageUrl,
            final Integer price,
            final String description,
            final List<Long> categoryIds
    ) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.description = description;
        this.categoryIds = categoryIds;
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

    public List<Long> getCategoryIds() {
        return categoryIds;
    }
}
