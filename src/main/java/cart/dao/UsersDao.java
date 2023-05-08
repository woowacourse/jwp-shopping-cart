package cart.dao;

import cart.entity.User;
import cart.entity.vo.Email;

import java.util.List;

public interface UsersDao {

    List<User> findAll();

    User findByEmail(final Email email);
}
