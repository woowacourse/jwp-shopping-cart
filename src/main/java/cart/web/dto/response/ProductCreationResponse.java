package cart.web.dto.response;

import cart.web.dto.request.ProductCreationRequest;

public class ProductCreationResponse {
    private Long id;
    private String name;
    private Integer price;
    private String category;
    private String imageUrl;

    public ProductCreationResponse() {
    }

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
