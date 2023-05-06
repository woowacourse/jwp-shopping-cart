package cart.dao.cart;

import cart.domain.cart.Cart;
import cart.domain.cart.Quantity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakeCartDao implements CartDao {

    private final List<Cart> carts = new ArrayList<>();

    @Override
    public void insert(final Cart cart) {
        carts.add(new Cart(cart.getProductId(), cart.getMemberId(), new Quantity(1)));
    }

    @Override
    public List<Cart> findAll() {
        return carts;
    }

    @Override
    public Optional<Cart> findByMemberIdAndProductId(final Long memberId, final Long productId) {
        return carts.stream()
            .filter(cart -> cart.getMemberId().equals(memberId) && cart.getProductId().equals(productId))
            .findFirst();
    }

    @Override
    public void deleteByMemberIdAndProductId(final Long memberId, final Long productId) {
        carts.removeIf(cart -> cart.getMemberId().equals(memberId) && cart.getProductId().equals(productId));
    }

    @Override
    public void update(final Cart cart) {
        carts.stream()
            .filter(it -> it.getMemberId().equals(cart.getMemberId()) && it.getProductId().equals(cart.getProductId()))
            .findFirst()
            .ifPresent(it -> carts.set(carts.indexOf(it), cart));
    }
}
