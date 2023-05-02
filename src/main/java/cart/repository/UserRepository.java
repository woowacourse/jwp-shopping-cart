package cart.repository;

import java.util.Optional;

import cart.domain.user.Email;
import cart.domain.user.User;

public interface UserRepository {

    Optional<User> findByEmail(final Email email);
}
