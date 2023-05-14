package cart.dto;

import cart.entity.CartItem;
import cart.entity.Product;

public final class UserCartResponse {
    private final Long id;
    private final String name;
    private final String image;
    private final int price;

    public UserCartResponse(final Long id, final String name, final String image, final int price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public static UserCartResponse from(final CartItem cartItem) {
        final Product product = cartItem.getProduct();

        return new UserCartResponse(cartItem.getId(), product.getName(), product.getImage(), product.getPrice());
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

    public int getPrice() {
        return price;
    }
}
