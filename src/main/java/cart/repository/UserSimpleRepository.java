package cart.repository;

import cart.domain.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class UserSimpleRepository implements UserRepository {
    List<User> users = Arrays.asList(new User("rosie@wooteco.com", "1234"),
            new User("poz@wooteco.com", "4321"));

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return users.stream().filter(user -> user.getEmail().equals(email))
                .findAny();
    }
}
