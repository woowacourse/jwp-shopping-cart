package cart.service;

import cart.entity.UserEntity;
import cart.repository.UserDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(final UserDao userDao) {
        this.userDao = userDao;
    }

    public List<UserEntity> findAll() {
        return userDao.findAll();
    }
}
