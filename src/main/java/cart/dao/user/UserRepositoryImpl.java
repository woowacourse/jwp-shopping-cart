package cart.dao.user;

import cart.domain.user.Email;
import cart.domain.user.Password;
import cart.domain.user.User;
import cart.domain.user.UserRepository;
import cart.exception.GlobalException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
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

    @Override
    public User findById(Long id) {
        UserEntity userEntity = userDao.findById(id)
                .orElseThrow(() -> new GlobalException("존재하지 않는 회원입니다."));

        return toUser(userEntity);
    }

    @Override
    public User findByEmailAndPassword(Email email, Password password) {
        UserEntity userEntity = userDao.findByEmailAndPassword(email.getEmail(), password.getPassword())
                .orElseThrow(() -> new GlobalException("존재하지 않는 회원입니다."));

        return toUser(userEntity);
    }

    @Override
    public boolean isRegisteredUser(Email email, Password password) {
        Optional<UserEntity> user = userDao.findByEmailAndPassword(email.getEmail(), password.getPassword());

        return user.isPresent();
    }

    @Override
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }
}
