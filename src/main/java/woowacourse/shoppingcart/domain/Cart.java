package woowacourse.shoppingcart.domain;

import java.util.List;
import java.util.Optional;

public class Cart {

    private final List<CartItem> value;

    public Cart(final List<CartItem> cartItems) {
        this.value = cartItems;
    }

    public Optional<CartItem> findByProductId(final Long productId) {
        return value.stream()
                .filter(v -> v.hasSameProductId(productId))
                .findFirst();
    }
}
