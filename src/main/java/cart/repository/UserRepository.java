package cart.repository;

import cart.domain.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();

    Optional<User> findByEmail(String email);

}
