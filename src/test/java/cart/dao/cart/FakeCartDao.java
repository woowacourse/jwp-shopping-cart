package cart.dao.cart;

import cart.domain.cart.Cart;
import cart.domain.cart.Quantity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FakeCartDao implements CartDao {

    private final List<Cart> carts = new ArrayList<>();

    @Override
    public void insert(final Cart cart) {
        carts.add(cart);
    }

    @Override
    public List<Cart> findAll() {
        return carts.stream()
                .map(it -> findByMemberIdAndProductId(it.getMemberId(), it.getProductId()).orElseThrow())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Cart> findByMemberIdAndProductId(final Long memberId, final Long productId) {
        final int quantity = (int) carts.stream()
                .filter(cart -> cart.getMemberId().equals(memberId) && cart.getProductId().equals(productId)).count();

        return carts.stream()
                .filter(cart -> cart.getMemberId().equals(memberId) && cart.getProductId().equals(productId))
                .findAny()
                .map(cart -> new Cart(cart.getId(), cart.getProductId(), cart.getMemberId(), new Quantity(quantity)));
    }

    @Override
    public void deleteByMemberIdAndProductId(final Long memberId, final Long productId) {
        carts.removeIf(cart -> cart.getMemberId().equals(memberId) && cart.getProductId().equals(productId));
    }
}
