package cart.repository;

import java.util.Optional;

import cart.dao.UserDao;
import cart.domain.user.Email;
import cart.domain.user.User;
import cart.entiy.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public class RdsUserRepository implements UserRepository {

    private final UserDao userDao;

    public RdsUserRepository(final UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Optional<User> findByEmail(final Email email) {
        return userDao.findByEmail(email.getValue()).map(UserEntity::toDomain);
    }
}
