package woowacourse.shoppingcart.domain;

import java.util.List;
import java.util.Optional;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

public class Carts {

    private final List<Cart> values;

    public Carts(final List<Cart> values) {
        this.values = values;
    }

    public void addOrUpdate(final Cart cart) {
        findCartByProduct(cart.getProduct())
                .ifPresentOrElse(Cart::addQuantity, () -> values.add(cart));
    }

    public Cart getCartHave(final Product product) {
        return findCartByProduct(product)
                .orElseThrow(InvalidCartItemException::new);
    }

    public Optional<Cart> findCartByProduct(final Product product) {
        return values.stream()
                .filter(value -> value.isSameProduct(product))
                .findFirst();
    }
}
