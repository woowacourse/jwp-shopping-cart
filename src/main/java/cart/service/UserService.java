package cart.service;

import cart.dao.UserDao;
import cart.dao.entity.User;
import cart.dto.UserResponse;
import cart.dto.UserResponses;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(final UserDao userDao) {
        this.userDao = userDao;
    }

    public UserResponses findAll() {
        final List<User> users = userDao.findAll();

        final List<UserResponse> userResponses = users.stream()
                .map(UserResponse::new)
                .collect(toList());
        return new UserResponses(userResponses);
    }
}
