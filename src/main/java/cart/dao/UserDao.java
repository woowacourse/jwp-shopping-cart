package cart.dao;

import cart.dao.entity.Users;

import java.util.Optional;

public interface UserDao {

    Optional<Users> findById(final Long id);
}
