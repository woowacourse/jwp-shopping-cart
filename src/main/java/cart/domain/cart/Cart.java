package cart.domain.cart;

import cart.domain.item.Item;
import java.util.List;

public class Cart {

    private final Long id;
    private final List<Item> items;

    public Cart(final Long id, final List<Item> items) {
        this.id = id;
        this.items = items;
    }

    public boolean isExistsItem(Item item) {
        return items.contains(item);
    }

    public Long getId() {
        return id;
    }

    public List<Item> getItems() {
        return items;
    }
}
