package cart.domain.cart;

import java.util.List;

public interface CartRepository {
    Long save(Cart cart);

    List<Cart> findAllByUserId(Long userId);

    void deleteById(Long id);
}
