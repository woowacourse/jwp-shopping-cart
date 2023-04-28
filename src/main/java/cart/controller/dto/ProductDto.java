package cart.controller.dto;

import cart.persistence.entity.Product;
import cart.persistence.entity.ProductCategory;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

public class ProductDto {

    private final Long id;

    @Length(min = 1, max = 25, message = "상품 이름의 길이는 {min} ~ {max}글자여야 합니다.")
    private final String name;

    private final String imageUrl;

    @NotNull(message = "상품 가격은 비어있을 수 없습니다.")
    @Range(min = 0, max = 10_000_000, message = "상품 가격은 {min} ~ {max}원까지 가능합니다.")
    private final Integer price;

    @NotNull(message = "상품 카테고리는 비어있을 수 없습니다.")
    private final ProductCategory category;

    public ProductDto(final Long id, final String name, final String imageUrl, final Integer price, final ProductCategory category) {
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

    public ProductCategory getCategory() {
        return category;
    }

    public Product toEntity() {
        return new Product(id, name, imageUrl, price, category);
    }
}
