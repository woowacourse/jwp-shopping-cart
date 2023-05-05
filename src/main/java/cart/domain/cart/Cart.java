package cart.domain.cart;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private final Long memberId;
    private final List<Item> items;

    public Cart(Long memberId, List<Item> items) {
        this.memberId = memberId;
        this.items = new ArrayList<>();

        if (items.size() > 0) {
            this.items.addAll(items);
        }
    }

    public boolean contains(Item item) {
        return items.contains(item);
    }

    public Long getMemberId() {
        return memberId;
    }

    public List<Item> getItems() {
        return new ArrayList<>(items);
    }
}
