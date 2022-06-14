package woowacourse.shoppingcart.domain;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.exception.badrequest.CartDuplicateException;
import woowacourse.exception.notfound.CartNotFoundException;

public class Carts {
    private final long customerId;
    private final List<Cart> carts;

    public Carts(final long customerId, final List<Cart> carts) {
        this.customerId = customerId;
        this.carts = carts;
    }

    public void addCart(final Cart cart) {
        validateAlreadyExists(cart);
        carts.add(cart);
    }

    private void validateAlreadyExists(final Cart cart) {
        final boolean exists = carts.stream()
                .map(Cart::getProduct)
                .collect(Collectors.toList())
                .contains(cart.getProduct());

        if (exists) {
            throw new CartDuplicateException();
        }
    }

    public void updateQuantity(final long productId, final int quantity) {
        final Cart updateTarget = carts.stream()
                .filter(cart -> cart.isSameProductId(productId))
                .findAny()
                .orElseThrow(CartNotFoundException::new);

        updateTarget.updateQuantity(quantity);
    }

    public List<Cart> getCartsByIds(final List<Long> cartIds) {
        return carts.stream()
                .filter(cart -> cartIds.contains(cart.getId()))
                .collect(Collectors.toList());
    }

    public void emptyCarts(final List<Cart> orderingCarts) {
        carts.removeAll(orderingCarts);
    }

    public long getCustomerId() {
        return customerId;
    }

    public List<Cart> getCarts() {
        return carts;
    }
}
