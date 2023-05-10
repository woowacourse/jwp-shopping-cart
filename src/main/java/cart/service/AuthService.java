package cart.service;

import cart.dao.UserDao;
import cart.domain.AuthInfo;
import cart.dto.AuthRequest;
import cart.exception.AuthorizationException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserDao userDao;

    public AuthService(UserDao userDao) {
        this.userDao = userDao;
    }

    public int findUserIdByAuth(AuthRequest authRequest) {
        AuthInfo authInfo = new AuthInfo(authRequest.getEmail(), authRequest.getPassword());
        Integer userId = userDao.selectByAuth(authInfo);
        if (userId == null) {
            throw new AuthorizationException("인가되지 않은 사용자 정보입니다.");
        }

        return userId.intValue();
    }
}
