package cart.dao.cart;

import cart.domain.cart.Cart;
import java.util.List;
import java.util.Optional;

public interface CartDao {

    void insert(Cart cart);

    List<Cart> findAll();

    Optional<Cart> findByProductId(Long productId);

    void deleteByProductId(Long productId);

    void update(Cart cart);
}
