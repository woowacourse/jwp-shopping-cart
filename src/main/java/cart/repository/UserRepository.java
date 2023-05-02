package cart.repository;

import cart.domain.user.Email;
import cart.domain.user.User;

public interface UserRepository {

    User findByEmail(final Email email);
}
