package cart.auth.service;

import cart.dto.UserAuthInfo;
import cart.exception.UserAuthenticationException;
import cart.persistence.dao.UserDao;
import cart.persistence.entity.UserEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserDao userDao;

    public AuthService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean validAuthentication(final UserAuthInfo userAuthInfo) {
        String email = userAuthInfo.getEmail();
        String password = userAuthInfo.getPassword();
        try {
            Long id = userDao.findUserIdByEmail(email);
            UserEntity user = userDao.findById(id).orElseThrow(() -> new UserAuthenticationException("사용자 정보가 없습니다"));
            return user.getPassword().equals(password);
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
