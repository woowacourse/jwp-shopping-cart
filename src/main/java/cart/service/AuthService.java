package cart.service;

import cart.controller.dto.request.LoginRequest;
import cart.database.dao.UserDao;
import cart.entity.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class AuthService {

    private final UserDao userDao;

    public AuthService(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserEntity loadUserByEmailAndPassword(LoginRequest request) {
        return userDao.findByEmailAndPassword(request.getEmail(), request.getPassword());
    }
}
