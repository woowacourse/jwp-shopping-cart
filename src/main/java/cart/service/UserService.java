package cart.service;

import cart.controller.dto.UserResponse;
import cart.dao.UserDao;
import cart.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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

    @Transactional(readOnly = true)
    public boolean checkLogin(final String email, final String password) {
        Optional<User> findUserByEmail = userDao.findBy(email);
        User user = findUserByEmail.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메일입니다."));
        return user.getPassword().equals(password);
    }
}
