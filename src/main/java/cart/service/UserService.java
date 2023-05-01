package cart.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import cart.dao.UserDao;
import cart.dao.entity.User;
import cart.dto.UserMapper;
import cart.dto.UserResponse;

@Service
public class UserService {
    private final static UserMapper USER_MAPPER = new UserMapper();
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<UserResponse> findAll() {
        final List<User> users = userDao.findAll();
        return users.stream()
                .map(USER_MAPPER::toResponse)
                .collect(Collectors.toUnmodifiableList());
    }
}
