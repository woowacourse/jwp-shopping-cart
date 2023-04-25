package cart.controller.dto;

public class ProductDto {

    private final Long id;
    private final String name;
    private final String imageUrl;
    private final int price;
    private final String category;

    public ProductDto(final Long id, final String name, final String imageUrl, final int price, final String category) {
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

    public int getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }
}
