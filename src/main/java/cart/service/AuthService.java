package cart.service;

import cart.controller.dto.request.LoginRequest;
import cart.controller.dto.response.UserResponse;
import cart.database.dao.UserDao;
import cart.entity.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public  class AuthService {

    private final UserDao userDao;

    public AuthService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public UserResponse basicLogin(LoginRequest request) {
        UserEntity userEntity = userDao.findByEmailAndPassword(request.getEmail(), request.getPassword());
        return new UserResponse(userEntity.getId(), userEntity.getEmail(), userEntity.getPassword());
    }
}
