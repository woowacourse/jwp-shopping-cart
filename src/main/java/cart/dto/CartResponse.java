package cart.dto;

public class CartResponse {

    private final long cartId;
    private final String imgUrl;
    private final String name;
    private final int price;

    public CartResponse(final long cartId, final String imgUrl, final String name, final int price) {
        this.cartId = cartId;
        this.imgUrl = imgUrl;
        this.name = name;
        this.price = price;
    }

    public long getCartId() {
        return cartId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
