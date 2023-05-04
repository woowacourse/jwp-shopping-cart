package cart.repository;

import java.util.List;

import cart.domain.cart.Cart;
import cart.domain.user.Email;
import cart.dto.CartResponse;

public interface CartRepository {

    CartResponse save(String email, Long productId);

    List<Cart> findByEmail(Email email);

    void deleteById(Long id);
}
