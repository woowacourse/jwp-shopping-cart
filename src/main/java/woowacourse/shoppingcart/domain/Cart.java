package woowacourse.shoppingcart.domain;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

public class Cart {

    private final List<CartItem> value;

    public Cart(List<CartItem> value) {
        this.value = value;
    }

    public List<CartItem> getValue() {
        return Collections.unmodifiableList(value);
    }

    public void validateCart(List<Long> cartItemIdsWithRequest) {
        List<Long> cartItemIds = getCartItemIds();
        if (!cartItemIds.containsAll(cartItemIdsWithRequest)) {
            throw new InvalidCartItemException();
        }
    }

    public List<CartItem> getExistingCartItem(List<Long> cartItemIds) {
        return value.stream()
                .filter(it -> cartItemIds.contains(it.getId()))
                .collect(Collectors.toList());
    }

    public List<Long> getCartItemIds() {
        return value.stream()
                .map(CartItem::getId)
                .collect(Collectors.toList());
    }
}
