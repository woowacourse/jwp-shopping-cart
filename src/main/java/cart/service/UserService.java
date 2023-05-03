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
    public List<User> findAll() {
        return userDao.findAll();
    }
}
