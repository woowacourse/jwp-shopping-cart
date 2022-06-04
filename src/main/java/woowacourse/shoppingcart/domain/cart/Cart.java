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
}
