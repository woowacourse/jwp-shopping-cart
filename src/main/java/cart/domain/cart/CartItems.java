package cart.domain.cart;

import java.util.ArrayList;
import java.util.List;

public class CartItems {

    private final List<CartItem> items;

    public CartItems(List<CartItem> items) {
        this.items = new ArrayList<>(items);
    }

    public void add(CartItem cartItem) {
        items.add(cartItem);
    }

    public void remove(int cartItemId) {
        if (cartItemId > items.size()) {
            throw new IllegalArgumentException("삭제할 수 없습니다.");
        }
        items.remove(cartItemId - 1);
    }

    public List<CartItem> getItems() {
        return List.copyOf(items);
    }
}
