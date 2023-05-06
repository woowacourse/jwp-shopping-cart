package cart.domain.cartitem;

import cart.dao.Dao;
import cart.dto.CartItemDto;
import java.util.List;

abstract class CartItemDao implements Dao<CartItem> {

    abstract List<CartItemDto> findByMemberId(Long id);

    abstract boolean isDuplicated(Long member_id, Long product_id);

    @Override
    public final void update(final CartItem entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean isExist(final Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final List<CartItem> findAll() {
        throw new UnsupportedOperationException();
    }
}
