package cart.web.dto.request;

public class ProductCreationRequest {
    private final String name;
    private final Integer price;
    private final String category;
    private final String imageUrl;

    public ProductCreationRequest(final String name, final Integer price, final String category,
                                  final String imageUrl) {
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
