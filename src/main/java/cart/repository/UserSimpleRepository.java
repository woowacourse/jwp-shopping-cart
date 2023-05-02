package cart.repository;

import cart.domain.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class UserSimpleRepository implements UserRepository {
    List<User> users = Arrays.asList(new User("rosie@wooteco.com", "1234"),
            new User("poz@wooteco.com", "4321"));

    @Override
    public Optional<User> findByEmail(String email) {
        return users.stream().filter(user -> user.getEmail().equals(email))
                .findAny();
    }
}
