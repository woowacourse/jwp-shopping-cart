package cart.domain.user;

import cart.web.controller.user.dto.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(final UserDao userDao) {
        this.userDao = userDao;
    }

    public List<UserResponse> getUsers() {
        final List<User> users = userDao.findAll();

        return users.stream()
                .map(user -> new UserResponse(user.getUserEmailValue(), user.getUserPasswordValue()))
                .collect(Collectors.toList());
    }
}
