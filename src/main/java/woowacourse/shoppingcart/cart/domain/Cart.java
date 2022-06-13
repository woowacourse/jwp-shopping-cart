package woowacourse.shoppingcart.cart.domain;

import java.util.List;
import java.util.Objects;
import woowacourse.shoppingcart.order.exception.notfound.NotFoundCartItemException;

public class Cart {

    private final List<CartItem> values;

    public Cart(final List<CartItem> values) {
        this.values = values;
    }

    public CartItem getItemById(final Long productId) {
        return values
                .stream()
                .filter(it -> it.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(NotFoundCartItemException::new);
    }

    public List<CartItem> getValues() {
        return values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cart cart = (Cart) o;
        return Objects.equals(values, cart.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(values);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "values=" + values +
                '}';
    }
}
