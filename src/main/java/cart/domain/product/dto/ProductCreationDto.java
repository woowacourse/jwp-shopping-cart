package cart.domain.product.dto;

public class ProductCreationDto {
    private final String name;
    private final Integer price;
    private final String category;
    private final String imageUrl;

    public ProductCreationDto(String name, Integer price, String category, String imageUrl) {
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
