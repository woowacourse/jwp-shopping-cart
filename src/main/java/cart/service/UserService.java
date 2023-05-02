package cart.service;

import cart.dao.UserDao;
import cart.dto.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserDao userDao;

    public UserService(final UserDao userDao) {
        this.userDao = userDao;
    }

    public List<UserDto> findAllUsers() {
        return userDao.findAll().stream()
                .map(UserDto::from)
                .collect(Collectors.toUnmodifiableList());
    }
}
