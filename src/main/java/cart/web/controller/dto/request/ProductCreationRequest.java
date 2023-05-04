package cart.web.controller.dto.request;

public class ProductCreationRequest {
    private String name;
    private Integer price;
    private String category;
    private String imageUrl;

    public ProductCreationRequest() {
    }

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
