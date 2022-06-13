package woowacourse.shoppingcart.domain;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Cart {

    private final List<CartItem> value;

    public Cart(List<CartItem> value) {
        this.value = value;
    }

    public List<CartItem> getValue() {
        return Collections.unmodifiableList(value);
    }

    public List<CartItem> getExistingIds(List<Long> cartIds) {
        return value.stream()
                .filter(it -> cartIds.contains(it.getId()))
                .collect(Collectors.toList());
    }

    public List<Long> getCartItemIds() {
        return value.stream()
                .map(CartItem::getId)
                .collect(Collectors.toList());
    }
}
