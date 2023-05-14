package cart.dto;

import cart.domain.ProductEntity;

public class ResponseProductDto {

    private Long id;
    private String name;
    private Integer price;
    private String image;

    public ResponseProductDto(final ProductEntity productEntity) {
        this.id = productEntity.getId();
        this.name = productEntity.getName();
        this.price = productEntity.getPrice();
        this.image = productEntity.getImage();
    }

    public ResponseProductDto() {
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
