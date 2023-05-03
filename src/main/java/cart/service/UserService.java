package cart.service;

import cart.dao.UsersDao;
import cart.entity.User;
import cart.exception.UserAuthorizationException;
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
                .map(user -> new UserDto(user.getEmail().getValue(), user.getPassword()))
                .collect(Collectors.toUnmodifiableList());
    }

    public void validateUser(final String email, final String password) {
        final User user = usersDao.findByEmail(email);
        if (!user.getPassword().equals(password)) {
            throw new UserAuthorizationException("사용자의 비밀번호가 일치하지 않습니다.");
        }
    }
}
