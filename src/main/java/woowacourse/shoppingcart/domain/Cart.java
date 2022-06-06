package woowacourse.shoppingcart.domain;

public class Cart {
    private final CartItem cartItem;
    private final int quantity;

    public Cart(CartItem cartItem, int quantity) {
        this.cartItem = cartItem;
        this.quantity = quantity;
    }

    public static Cart of(Long id, String name, int price, String thumbnail, int quantity) {
        return new Cart(CartItem.of(id, name, price, thumbnail), quantity);
    }

    public CartItem getCartItem() {
        return cartItem;
    }

    public Product getProduct() {
        return cartItem.getProduct();
    }

    public int getQuantity() {
        return quantity;
    }
}
