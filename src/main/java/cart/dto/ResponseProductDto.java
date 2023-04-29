package cart.dto;

import cart.dao.entity.ProductEntity;

public class ResponseProductDto {

    private final Long id;
    private final String name;
    private final Integer price;
    private final String image;

    public ResponseProductDto(final ProductEntity productEntity) {
        this.id = productEntity.getId();
        this.name = productEntity.getName();
        this.price = productEntity.getPrice();
        this.image = productEntity.getImage();
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

    public String getImage() {
        return image;
    }
}
