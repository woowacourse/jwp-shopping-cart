package cart.dao;

import cart.domain.user.User;

import java.util.List;

public interface UserDao {

    List<User> findAll();
}
