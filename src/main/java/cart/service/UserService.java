package cart.service;

import cart.controller.dto.UserRequest;
import cart.controller.dto.UserResponse;
import cart.dao.UserDao;
import cart.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(final UserDao userDao) {
        this.userDao = userDao;
    }

    public Long saveUser(final UserRequest userRequest) {
        User user = new User(userRequest.getEmail(), userRequest.getPassword());
        return userDao.save(user);
    }

    public List<UserResponse> loadAllUser() {
        List<User> users = userDao.findAll();
        return users.stream()
                    .map(UserResponse::from)
                    .collect(Collectors.toList());
    }

    public UserResponse loadUser(final Long userId) {
        User user = userDao.findBy(userId);
        return UserResponse.from(user);
    }
}
