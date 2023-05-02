package cart.repository;

import cart.domain.user.Email;
import cart.domain.user.User;

public class StubUserRepository implements UserRepository {

    @Override
    public User findByEmail(final Email email) {
        return new User("a@a.com", "password1");
    }
}
