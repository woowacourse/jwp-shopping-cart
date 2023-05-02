package cart.service;

import cart.dao.UsersDao;
import cart.service.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UsersDao usersDao;

    public UserService(final UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    public List<UserDto> findAll() {
        return usersDao.findAll().stream()
                .map(user -> new UserDto(user.getEmail(), user.getPassword()))
                .collect(Collectors.toUnmodifiableList());
    }
}
