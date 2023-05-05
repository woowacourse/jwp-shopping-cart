package cart.domain.cartitem;

import cart.dao.Dao;
import java.util.List;

abstract class CartItemDao implements Dao<CartItem> {

    abstract List<CartItem> findByMemberId(Long id);

    @Override
    public final void update(final CartItem entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final List<CartItem> findAll() {
        throw new UnsupportedOperationException();
    }
}
