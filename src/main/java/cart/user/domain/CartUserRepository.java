package cart.user.domain;

import java.util.List;
import java.util.Optional;

public interface CartUserRepository {
    Optional<CartUser> findByEmail(String email);

    Long save(CartUser cartUser);

    List<CartUser> findAll();
}
