package cart.dao.user;

import cart.domain.user.UserRepository;
import cart.domain.user.Email;
import cart.domain.user.Password;
import cart.domain.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final UserDao userDao;

    public UserRepositoryImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Long save(User user) {
        UserEntity userEntity = toEntity(user);

        return userDao.insert(userEntity);
    }

    private UserEntity toEntity(User user) {
        return new UserEntity(
                user.getId(),
                user.getEmail(),
                user.getPassword()
        );
    }

    @Override
    public List<User> findAll() {
        List<UserEntity> allUserEntities = userDao.findAll();

        return allUserEntities.stream()
                .map(this::toUser)
                .collect(Collectors.toList());
    }

    private User toUser(UserEntity userEntity) {
        return new User(
                Email.from(userEntity.getEmail()),
                Password.from(userEntity.getPassword()),
                userEntity.getId()
        );
    }
}
