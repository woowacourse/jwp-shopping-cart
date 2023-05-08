package cart.domain.cart;

import cart.domain.item.Item;
import cart.exception.cart.CartAlreadyExistsException;
import cart.exception.cart.CartNotFoundException;
import java.util.List;

public class Cart {

    private final Long id;
    private final List<Item> items;

    public Cart(Long id, List<Item> items) {
        this.id = id;
        this.items = items;
    }

    public void addItem(Item item) {
        validateAlreadyExistsItem(item);
        items.add(item);
    }

    private void validateAlreadyExistsItem(Item item) {
        if (isExistsItem(item)) {
            throw new CartAlreadyExistsException("이미 장바구니에 존재하는 상품입니다.");
        }
    }

    public void removeItem(Item item) {
        validateNotExistsItem(item);
        items.remove(item);
    }

    private void validateNotExistsItem(Item item) {
        if (!items.contains(item)) {
            throw new CartNotFoundException("장바구니에 존재하지 않는 상품입니다.");
        }
    }

    private boolean isExistsItem(Item item) {
        return items.contains(item);
    }

    public Long getId() {
        return id;
    }

    public List<Item> getItems() {
        return items;
    }
}
