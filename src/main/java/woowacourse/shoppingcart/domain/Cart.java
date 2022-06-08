package woowacourse.shoppingcart.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import woowacourse.shoppingcart.exception.IllegalCartItemException;

public class Cart {
    private final Set<CartItem> items;

    public Cart() {
        this.items = new HashSet<>();
    }

    public Cart(List<CartItem> items) {
        this.items = new HashSet<>(items);
    }

    public void addItem(CartItem item) {
        boolean added = items.add(item);
        if (!added) {
            throw new IllegalCartItemException("이미 담긴 상품입니다.");
        }
    }

    public Set<CartItem> getItems() {
        return Collections.unmodifiableSet(items);
    }
}
