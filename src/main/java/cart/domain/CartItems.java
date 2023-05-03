package cart.domain;

import java.util.ArrayList;
import java.util.List;

public class CartItems {
    private final List<CartItem> cartItems;

    public CartItems(List<CartItem> cartItems) {
        this.cartItems = new ArrayList<>(cartItems);
    }

    public void add(CartItem newCartItem) {
        cartItems.add(newCartItem);
    }

    public void remove(Long cardItemId) {
        cartItems.removeIf(cartItem -> cartItem.getId().equals(cardItemId));
    }

    public List<CartItem> getCartItems() {
        return new ArrayList<>(cartItems);
    }
}
