package cart.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart {

    private final User owner;
    private final List<CartItem> items;

    public Cart(User user) {
        this(user, Collections.emptyList());
    }

    public Cart(User owner, List<CartItem> items) {
        validateNoDuplicate(items);
        this.owner = owner;
        this.items = new ArrayList<>(items);
    }

    private void validateNoDuplicate(List<CartItem> items) {
        if (items.size() != items.stream().distinct().count()) {
            throw new IllegalArgumentException("중복된 상품이 있습니다");
        }
    }

    private void validateDoesNotHave(CartItem item) {
        if (items.contains(item)) {
            throw new IllegalArgumentException("이미 담긴 상품입니다");
        }
    }

    private void validateHas(CartItem item) {
        if (!items.contains(item)) {
            throw new IllegalArgumentException("없는 상품입니다");
        }
    }

    public void add(CartItem item) {
        validateDoesNotHave(item);
        items.add(item);
    }

    public void delete(CartItem item) {
        validateHas(item);
        items.remove(item);
    }

    public User getOwner() {
        return owner;
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(items);
    }
}
