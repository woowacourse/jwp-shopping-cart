package cart.controller.dto.response;

import cart.entity.ProductEntity;

public class ProductResponse {

    private final Long id;
    private final String name;
    private final String imageUrl;
    private final int price;

    public ProductResponse(ProductEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.imageUrl = entity.getImageUrl();
        this.price = entity.getPrice();
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
}
