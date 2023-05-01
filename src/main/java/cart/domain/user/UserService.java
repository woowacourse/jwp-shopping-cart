package cart.domain.user;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(final UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getUsers() {
        return userDao.findAll();
    }
}
