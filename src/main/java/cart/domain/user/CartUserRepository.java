package cart.domain.user;

import java.util.List;

public interface CartUserRepository {
    CartUser findByEmail(String email);

    Long save(CartUser cartUser);

    List<CartUser> findAll();
}
