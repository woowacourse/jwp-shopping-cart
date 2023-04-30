package cart.web.controller.dto.response;

import cart.web.controller.dto.request.ProductModificationRequest;

public class ProductModificationResponse {
    private final Long id;
    private final String name;
    private final Integer price;
    private final String category;
    private final String imageUrl;

    public ProductModificationResponse(ProductModificationRequest request) {
        this.id = request.getId();
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
