package cart.dto.response;

import cart.dao.entity.ProductEntity;

public class CartResponse {

    private Long id;
    private String name;
    private Integer price;
    private String image;

    public CartResponse() {
    }

    public CartResponse(final Long id, final String name, final Integer price, final String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public static CartResponse from(final ProductEntity productEntity) {
        return new CartResponse(productEntity.getId(), productEntity.getName(), productEntity.getPrice(), productEntity.getImage());
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
