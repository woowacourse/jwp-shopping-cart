package cart.repository;

import java.util.List;

import cart.domain.cart.Cart;
import cart.domain.user.Email;

public interface CartRepository {

    void save(String email, Long productId);

    List<Cart> findByEmail(Email email);

    void deleteById(Long id);
}
