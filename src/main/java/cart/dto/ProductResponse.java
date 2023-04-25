package cart.dto;

import cart.persistence.entity.ProductEntity;

public class ProductResponse {

    private final Long id;
    private final String name;
    private final byte[] image;
    private final int price;

    private ProductResponse(final Long id, final String name, final byte[] image, final int price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public static ProductResponse from(final ProductEntity productEntity) {
        return new ProductResponse(productEntity.getId(), productEntity.getName(),
            productEntity.getImage(), productEntity.getPrice());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public byte[] getImage() {
        return image;
    }

    public Integer getPrice() {
        return price;
    }
}
