package cart.repository;

import java.util.List;
import java.util.Optional;

import cart.domain.cart.Cart;
import cart.domain.user.Email;
import cart.dto.CartResponse;
import cart.entiy.CartEntity;

public interface CartRepository {

    CartResponse save(String email, Long productId);

    List<Cart> findByEmail(Email email);

    Optional<CartEntity> findById(Long id);

    void deleteById(Long id);
}
