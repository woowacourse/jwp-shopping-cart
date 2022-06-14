package woowacourse.shoppingcart.domain;

import java.util.List;

public class Cart {

    private final List<CartItem> cartItems;

    public Cart(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public boolean isContains(CartItem cartItem) {
        return cartItems.stream()
            .anyMatch(it -> it.getId().equals(cartItem.getId()));
    }
}
