package cart.dao;

import cart.entity.User;

import java.util.List;

public interface UsersDao {

    List<User> findAll();

    User findByEmail(final String email);
}
