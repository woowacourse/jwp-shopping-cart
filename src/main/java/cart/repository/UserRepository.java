package cart.repository;

import java.util.List;
import java.util.Optional;

import cart.domain.user.Email;
import cart.domain.user.User;

public interface UserRepository {

    List<User> findAll();

    Optional<User> findByEmail(final Email email);
}
