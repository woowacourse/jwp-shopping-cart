package cart.service;

import cart.dao.UserDao;
import cart.domain.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    public static final String USER_ID_NOT_EXIST_ERROR_MESSAGE = "존재하지 않는 사용자 ID 입니다.";
    public static final String USER_EMAIL_NOT_EXIST_ERROR_MESSAE = "존재하지 않는 사용자 email 입니다.";
    
    private final UserDao userDao;

    public UserService(final UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public Long save(final String email, final String password) {
        final User user = new User(email, password);
        return userDao.insert(user);
    }

    @Transactional(readOnly = true)
    public User findByEmail(final String email) {
        return userDao.findByEmail(email).orElseThrow(() -> {
            throw new IllegalArgumentException(USER_EMAIL_NOT_EXIST_ERROR_MESSAE);
        });
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userDao.findAll();
    }

    public void validateUserIdExist(final Long id) {
        if (!isUserIdExist(id)) {
            throw new IllegalArgumentException(USER_ID_NOT_EXIST_ERROR_MESSAGE);
        }
    }

    private boolean isUserIdExist(final Long id) {
        return userDao.isExist(id);
    }
}
