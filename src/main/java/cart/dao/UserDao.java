package cart.dao;

import cart.domain.user.User;

import java.util.List;

public interface UserDao {

    Long insert(User user);

    User findById(Long id);

    User findByEmail(String email);

    List<User> findAll();
}
