package cart.entity;

public class CartProduct {
    private final int cartId;
    private final String name;
    private final int price;
    private final String image;


    public CartProduct(int cartId, String name, int price, String image) {
        this.cartId = cartId;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public int getCartId() {
        return cartId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
