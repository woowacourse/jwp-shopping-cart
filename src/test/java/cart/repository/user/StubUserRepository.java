package cart.repository.user;

import cart.domain.user.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StubUserRepository implements UserRepository {

    private final List<User> users = new ArrayList<>();
    private final long maxUserId = 1;

    @Override
    public User save(final User user) {
        final User newUser = new User(maxUserId, user.getEmail().getValue(), user.getPassword().getValue());
        users.add(newUser);
        return newUser;
    }

    @Override
    public boolean existsByEmailAndPassword(final String email, final String password) {
        for (final User user : users) {
            if (user.getEmail().getValue().equals(email) && user.getPassword().getValue().equals(password)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Optional<User> findByEmail(final String email) {
        for (final User user : users) {
            if (user.getEmail().getValue().equals(email)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return users;
    }
}
