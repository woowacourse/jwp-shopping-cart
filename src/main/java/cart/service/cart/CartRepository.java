package cart.service.cart;

import cart.domain.cart.Cart;

import java.util.List;

public interface CartRepository {
    Long save(Cart cart,Long userId);

    List<Cart> findAllByUserId(Long userId);

    void deleteById(Long id);
}
