package cart.dao;

import cart.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Long insert(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    List<User> findAll();
}
