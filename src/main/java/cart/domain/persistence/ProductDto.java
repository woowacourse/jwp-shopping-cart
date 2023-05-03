package cart.domain.persistence;

public class ProductDto {

    private final long cartId;
    private final String name;
    private final int price;
    private final String imageUrl;

    public ProductDto(final long cartId, final String name, final int price, final String imageUrl) {
        this.cartId = cartId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public long getCartId() {
        return cartId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
