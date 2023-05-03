package cart.dto;

import cart.entity.ProductEntity;

public class CartItemDto {

    private final Long id;
    private final String name;
    private final String image;
    private final Integer price;

    private CartItemDto(final Long id, final String name, final String image, final Integer price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public static CartItemDto from(final Long id, final ProductEntity productEntity) {
        return new CartItemDto(id, productEntity.getName(), productEntity.getImage(), productEntity.getPrice());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Integer getPrice() {
        return price;
    }
}
