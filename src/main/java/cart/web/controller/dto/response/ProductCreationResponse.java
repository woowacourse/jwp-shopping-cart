package cart.web.controller.dto.response;

import cart.web.controller.dto.request.ProductCreationRequest;

public class ProductCreationResponse {
    private final Long id;
    private final String name;
    private final Integer price;
    private final String category;
    private final String imageUrl;

    public ProductCreationResponse(Long id, ProductCreationRequest request) {
        this.id = id;
        this.name = request.getName();
        this.price = request.getPrice();
        this.category = request.getCategory();
        this.imageUrl = request.getImageUrl();
    }

    public Long getId() {
        return id;
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
