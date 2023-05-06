package cart.domain.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private final List<User> users = new ArrayList<>();
    private long serial = 0;

    @Override
    public Long save(User userToSave) {
        this.users.add(User.create(
                getSerial(),
                userToSave.getEmail(),
                userToSave.getPassword(),
                userToSave.getName().orElse(null),
                userToSave.getPhoneNumber().orElse(null)
        ));
        return this.serial;
    }

    private long getSerial() {
        this.serial += 1;
        return this.serial;
    }

    @Override
    public List<User> findAll() {
        return this.users;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }
}
