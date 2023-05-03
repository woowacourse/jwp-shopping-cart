package cart.repository;

import java.util.List;
import java.util.Optional;

import cart.domain.user.Email;
import cart.domain.user.User;

public class StubUserRepository implements UserRepository {

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findByEmail(final Email email) {
        return Optional.of(new User("a@a.com", "password1"));
    }
}
