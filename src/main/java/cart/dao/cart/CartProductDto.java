package cart.dao.cart;

public class CartProductDto {

    private final Long cartId;
    private final String name;
    private final Integer price;
    private final String imageUrl;

    public CartProductDto(final Long cartId, final String name, final Integer price, final String imageUrl) {
        this.cartId = cartId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Long getCartId() {
        return cartId;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
