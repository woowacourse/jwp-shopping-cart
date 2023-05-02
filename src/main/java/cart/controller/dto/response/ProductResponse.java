package cart.controller.dto.response;

import cart.entity.ProductEntity;

public class ProductResponse {

    private final Long id;
    private final String name;
    private final String imageUrl;
    private final int price;

    private ProductResponse(Long id, String name, String imageUrl, int price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public static ProductResponse of(final ProductEntity entity) {
        return new ProductResponse(
                entity.getId(),
                entity.getName(),
                entity.getImageUrl(),
                entity.getPrice()
        );
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
