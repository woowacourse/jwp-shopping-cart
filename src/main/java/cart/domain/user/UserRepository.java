package cart.domain.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Long save(User user);

    List<User> findAll();

    Optional<User> findByEmail(String email);
}
