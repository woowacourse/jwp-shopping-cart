package cart.service;

import cart.controller.dto.UserRequest;
import cart.dao.UserDao;
import cart.domain.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(final UserDao userDao) {
        this.userDao = userDao;
    }

    public Long saveUser(UserRequest userRequest) {
        User user = new User(userRequest.getEmail(), userRequest.getPassword());
        return userDao.save(user);
    }
}
