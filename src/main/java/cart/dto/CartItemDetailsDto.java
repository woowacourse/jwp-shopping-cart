package cart.dto;

import cart.entity.ProductEntity;

public class CartItemDetailsDto {

    private final long id;
    private final String name;
    private final String image;
    private final int price;

    private CartItemDetailsDto(final long id, final String name, final String image, final int price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public static CartItemDetailsDto from(final long id, final ProductEntity productEntity) {
        return new CartItemDetailsDto(id, productEntity.getName(), productEntity.getImage(), productEntity.getPrice());
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getPrice() {
        return price;
    }
}
