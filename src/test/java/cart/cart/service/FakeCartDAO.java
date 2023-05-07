package cart.cart.service;

import cart.cart.dao.CartDAO;
import cart.cart.domain.Cart;
import cart.cart.dto.CartRequestDTO;

import java.util.List;

public class FakeCartDAO implements CartDAO {

    @Override
    public Cart create(final CartRequestDTO cartRequestDTO) {
        return null;
    }

    @Override
    public Cart find(final CartRequestDTO cartRequestDTO) {
        return null;
    }

    @Override
    public List<Cart> findUserCart(final long userId) {
        return List.of(
                Cart.of(1L, 1L, 1L),
                Cart.of(2L, 1L, 2L)
        );
    }

    @Override
    public void delete(final Cart cart) {

    }

    @Override
    public void clear(final long userId) {

    }
}
