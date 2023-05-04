package cart.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public User save(final User user) {
        final UserEntity userEntity = userDao.insert(UserEntity.from(user));
        return userEntity.toDomain();
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll()
                .stream()
                .map(UserEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findByEmail(final Email email) {
        return userDao.findByEmail(email.getValue()).map(UserEntity::toDomain);
    }
}
