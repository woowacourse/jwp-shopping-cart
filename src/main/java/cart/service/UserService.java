package cart.service;

import cart.controller.dto.UserResponse;
import cart.dao.UserDao;
import cart.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(final UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional(readOnly = true)
    public List<UserResponse> loadAllUser() {
        List<User> allUser = userDao.findAll();
        return allUser.stream()
                .map(UserResponse::from)
                .collect(Collectors.toList());
    }
}
