package cart.dto.response;

import cart.dao.entity.ProductEntity;

public class ResponseCartProductDto {

    private final Long id;
    private final String name;
    private final Integer price;
    private final String image;

    private ResponseCartProductDto(final Long id, final String name, final Integer price, final String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public static ResponseCartProductDto of(Long cartIdByProduct, ProductEntity productEntity) {
        return new ResponseCartProductDto(cartIdByProduct, productEntity.getName(), productEntity.getPrice(), productEntity.getImage());
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
