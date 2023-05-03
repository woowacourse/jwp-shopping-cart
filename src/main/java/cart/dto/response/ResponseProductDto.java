package cart.dto.response;

import cart.dao.entity.ProductEntity;

public class ResponseProductDto {

    private final Long id;
    private final String name;
    private final Integer price;
    private final String image;

    public ResponseProductDto(final Long id, final String name, final Integer price, final String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public static ResponseProductDto transferEntityToDto(final ProductEntity productEntity) {
        return new ResponseProductDto(productEntity.getId(), productEntity.getName(), productEntity.getPrice(), productEntity.getImage());
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
