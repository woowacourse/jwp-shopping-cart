package cart.domain.user;

import java.util.List;

public interface UserRepository {
    Long save(User user);

    List<User> findAll();
}
