package woowacourse.shoppingcart.domain.cart;

import java.util.List;

public class Cart {

    private final List<CartItem> items;

    public Cart(List<CartItem> items) {
        this.items = items;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public boolean contains(CartItem cartItem) {
        return items.stream()
            .anyMatch(item -> item.getId().equals(cartItem.getId()));
    }
}
