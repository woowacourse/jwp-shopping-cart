package cart.dto.cartitem;

public class CartItemResponse {

    private final Long cartItemId;
    private final String name;
    private final String imageUrl;
    private final int price;
    private final int itemCount;

    public CartItemResponse(final Long cartItemId, final String name, final String imageUrl, final int price, final int itemCount) {
        this.cartItemId = cartItemId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.itemCount = itemCount;
    }

    public CartItemResponse(final CartItem cartItem) {
        this(
            cartItem.getProductId(),
            cartItem.getName(),
            cartItem.getImageUrl(),
            cartItem.getPrice(),
            cartItem.getCount()
        );
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPrice() {
        return price;
    }

    public int getItemCount() {
        return itemCount;
    }
}
