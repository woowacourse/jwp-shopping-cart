package cart.dao.user;

import java.util.List;

import cart.dao.entity.User;

public interface UserDao {

    Long saveUser(User user);

    List<User> findAll();
}
