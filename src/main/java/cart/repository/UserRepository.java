package cart.repository;

import cart.domain.User;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);

}
