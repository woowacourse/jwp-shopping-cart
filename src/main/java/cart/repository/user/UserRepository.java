package cart.repository.user;

import cart.domain.user.User;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    boolean existsByEmailAndPassword(String email, String password);

    Optional<User> findByEmail(String email);
}
