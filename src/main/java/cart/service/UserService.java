package cart.service;

import cart.controller.dto.response.UserResponse;
import cart.database.dao.UserDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<UserResponse> findAll() {
        return userDao.findAll().stream()
                .map(userEntity -> new UserResponse(userEntity.getId(), userEntity.getEmail(), userEntity.getPassword()))
                .collect(Collectors.toList());
    }
}
