package cart.domain.cart;

import cart.domain.item.Item;
import cart.exception.cart.CartAlreadyExistsException;
import cart.exception.cart.CartNotChangeException;
import cart.exception.cart.CartNotFoundException;
import java.util.List;
import java.util.Optional;

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
        if (items.contains(item)) {
            throw new CartNotFoundException("장바구니에 존재하지 않는 상품입니다.");
        }
    }

    private boolean isExistsItem(Item item) {
        return items.contains(item);
    }

    public Item findDifferentItem(List<Item> items) {
        List<Item> shorter = this.items;
        List<Item> longer = items;

        if (shorter.size() > longer.size()) {
            shorter = items;
            longer = this.items;
        }

        return calculateDifferentItem(shorter, longer)
                .orElseThrow(() -> new CartNotChangeException("장바구니의 변화가 없습니다."));
    }

    private Optional<Item> calculateDifferentItem(List<Item> shorter, List<Item> longer) {
        return longer.stream()
                .filter(item -> !shorter.contains(item))
                .findAny();
    }

    public Long getId() {
        return id;
    }

    public List<Item> getItems() {
        return items;
    }
}
