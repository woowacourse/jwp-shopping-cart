package woowacourse.shoppingcart.domain;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.exception.notfound.CartNotFoundException;

public class Carts {
    private final long memberId;
    private final List<Cart> carts;

    public Carts(final long memberId, final List<Cart> carts) {
        this.memberId = memberId;
        this.carts = carts;
    }

    public void addCart(final Cart cart) {
        carts.add(cart);
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

    public long getMemberId() {
        return memberId;
    }

    public List<Cart> getCarts() {
        return carts;
    }
}
