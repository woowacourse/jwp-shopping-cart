package cart.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import cart.controller.dto.CartResponse;
import cart.domain.cart.Cart;
import cart.domain.user.Email;
import cart.entiy.CartEntity;

public class StubCartRepository implements CartRepository {

    private final Map<Long, CartEntity> cartEntityMap = new HashMap<>();
    private final AtomicLong maxId = new AtomicLong();

    @Override
    public CartResponse save(final String email, final Long productId) {
        final long currentId = maxId.incrementAndGet();

        final CartEntity saved = new CartEntity(currentId, email, productId);
        cartEntityMap.put(currentId, saved);
        return new CartResponse(currentId, email, productId);
    }

    @Override
    public List<Cart> findByEmail(final Email email) {
        return null;
    }

    @Override
    public Optional<CartEntity> findById(final Long id) {
        final CartEntity cartEntity = cartEntityMap.get(id);
        if (cartEntity == null) {
            return Optional.empty();
        }
        return Optional.of(cartEntity);
    }

    @Override
    public void deleteById(final Long id) {
        cartEntityMap.remove(id);
    }
}
