package cart.service;

import cart.dao.UserDao;
import cart.domain.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

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
            throw new IllegalArgumentException("존재하지 않는 사용자 email 입니다.");
        });
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userDao.findAll();
    }

    public void validateExistUserId(final Long id) {
        if (userDao.findById(id).isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 사용자 id 입니다.");
        }
    }
}
