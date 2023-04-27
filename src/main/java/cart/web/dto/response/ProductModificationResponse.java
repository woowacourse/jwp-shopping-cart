package cart.web.dto.response;

import cart.web.dto.request.ProductModificationRequest;

public class ProductModificationResponse {
    private Long id;
    private String name;
    private Integer price;
    private String category;
    private String imageUrl;

    public ProductModificationResponse() {
    }

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
