package cart.controller.dto;

import cart.persistence.entity.ProductCategory;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProductDto {

    private final Long id;

    @NotBlank
    @Length(min = 1, max = 25)
    private final String name;

    private final String imageUrl;

    @NotNull
    @Range(min = 0, max = 10_000_000)
    private final Integer price;

    @NotNull
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
}
