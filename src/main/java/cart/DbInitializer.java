package cart;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import cart.dao.entity.User;
import cart.dao.user.UserDao;

@Component
public class DbInitializer {
    private final UserDao userDao;

    public DbInitializer(UserDao userDao) {
        this.userDao = userDao;
    }

    @PostConstruct
    public void initialize() {
        userDao.saveUser(new User("ahdjd5@gmail.com", "qwer1234"));
        userDao.saveUser(new User("ahdjd5@naver.com", "qwer1234"));
    }
}
