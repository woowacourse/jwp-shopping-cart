package cart.service;

import cart.dao.JdbcUserDao;
import cart.dao.UserResponses;
import cart.dao.entity.Users;
import cart.dto.AuthUser;
import cart.dto.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static java.util.stream.Collectors.toList;

@Service
public class UserService {

    private final JdbcUserDao userDao;

    public UserService(final JdbcUserDao userDao) {
        this.userDao = userDao;
    }

    public UserResponses findAll() {
        final List<Users> users = userDao.findAll();

        final List<UserResponse> userResponses = users.stream()
                .map(UserResponse::new)
                .collect(toList());
        return new UserResponses(userResponses);
    }

    public AuthUser findByAuthPrincipal(final String email, final String password) {
        final Users findUser = userDao.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("해당 사용자를 찾을 수 없습니다."));

        findUser.validatePassword(password);

        return new AuthUser(findUser);
    }
}
