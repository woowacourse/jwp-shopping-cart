package cart.dao.user;

import java.util.List;

import cart.dao.entity.User;

public interface UserDao {
    List<User> findAll();
}
