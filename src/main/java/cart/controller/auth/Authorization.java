package cart.controller.auth;

import cart.dao.UserDao;
import cart.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class Authorization {

    private final UserDao userDao;

    public Authorization(final UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional(readOnly = true)
    public boolean checkLogin(final String email, final String password) {
        Optional<User> findUserByEmail = userDao.findBy(email);
        if (findUserByEmail.isEmpty()) {
            return false;
        }
        User user = findUserByEmail.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메일입니다."));
        return user.getPassword().equals(password);
    }
}
