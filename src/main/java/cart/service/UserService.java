package cart.service;

import cart.controller.dto.UserRequest;
import cart.controller.dto.UserResponse;
import cart.dao.UserDao;
import cart.domain.User;
import cart.exception.NotFoundResultException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(final UserDao userDao) {
        this.userDao = userDao;
    }

    public Long saveUser(final UserRequest userRequest) {
        User user = new User.Builder()
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .build();
        return userDao.save(user);
    }

    public List<UserResponse> loadAllUser() {
        List<User> users = userDao.findAll();
        return users.stream()
                    .map(UserResponse::from)
                    .collect(Collectors.toList());
    }

    public UserResponse loadUser(final Long userId) {
        Optional<User> findUser = userDao.findBy(userId);
        User user = findUser.orElseThrow(() -> new NotFoundResultException("존재하지 않는 사용자 입니다."));
        return UserResponse.from(user);
    }
}
