package cart.dto.request;

import java.util.List;

public class ProductRequestDto {

    private final String name;
    private final String imageUrl;
    private final Integer price;
    private final String description;
    private final List<Long> categoryIds;

    public ProductRequestDto(final String name, final String imageUrl, final Integer price, final String description,
        final List<Long> categoryIds) {
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
