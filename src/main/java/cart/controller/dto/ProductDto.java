package cart.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProductDto {

    private final Long id;

    @NotBlank(message = "상품 이름은 비어있을 수 없습니다.")
    private final String name;

    private final String imageUrl;

    @NotNull(message = "상품 가격은 비어있을 수 없습니다.")
    private final Integer price;

    @NotNull(message = "상품 카테고리는 비어있을 수 없습니다.")
    private final String category;

    public ProductDto(final Long id, final String name, final String imageUrl, final Integer price,
                      final String category) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.category = category;
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

    public String getCategory() {
        return category;
    }
}
