package cart.domain.product.service.dto;

public class ProductCreationDto {
    private final String name;
    private final Integer price;
    private final String category;
    private final String imageUrl;

    public ProductCreationDto(final String name, final Integer price, final String category, final String imageUrl) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
