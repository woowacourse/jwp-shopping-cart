package cart.domain;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final List<CartItem> cartItems;

    public Cart(List<CartItem> cartItems) {
        this.cartItems = new ArrayList<>(cartItems);
    }

    public void add(CartItem newCartItem) {
        cartItems.add(newCartItem);
    }

    public List<CartItem> getCartItems() {
        return new ArrayList<>(cartItems);
    }
}
