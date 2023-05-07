package cart.repository.user;

import cart.domain.user.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();

    Optional<User> findByEmail(String email);

}
