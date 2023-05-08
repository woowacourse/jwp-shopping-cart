package cart.dao;

import cart.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Long insert(User user);

    boolean isExist(final long id);

    Optional<User> findById(long id);

    Optional<User> findByEmail(String email);

    List<User> findAll();
}
