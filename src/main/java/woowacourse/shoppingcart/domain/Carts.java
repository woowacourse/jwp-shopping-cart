package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.domain.cart.Cart;
import woowacourse.shoppingcart.domain.cart.Quantity;
import woowacourse.shoppingcart.domain.product.Product;

import java.util.List;

public class Carts {

    private final List<Cart> carts;

    public Carts(final List<Cart> carts) {
        this.carts = carts;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public Quantity findQuantity(final Product product) {
        return carts.stream()
                .filter(cart -> cart.isSame(product))
                .findFirst()
                .map(Cart::getQuantity)
                .orElse(new Quantity(Quantity.GUEST_QUANTITY));
    }
}
