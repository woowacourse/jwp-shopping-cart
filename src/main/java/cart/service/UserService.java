package cart.service;

import cart.dao.UserDao;
import cart.dao.UserResponses;
import cart.dao.entity.Users;
import cart.dto.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static java.util.stream.Collectors.toList;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(final UserDao userDao) {
        this.userDao = userDao;
    }

    public UserResponse findById(final Long id) {
        final Users user = userDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));

        return new UserResponse(user);
    }

    public UserResponses findAll() {
        final List<Users> users = userDao.findAll();

        final List<UserResponse> userResponses = users.stream()
                .map(UserResponse::new)
                .collect(toList());
        return new UserResponses(userResponses);
    }
}
