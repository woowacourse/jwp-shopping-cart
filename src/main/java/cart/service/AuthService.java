package cart.service;

import cart.dao.UserDao;
import cart.dto.AuthInfo;
import cart.exception.AuthorizationException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserDao userDao;

    public AuthService(UserDao userDao) {
        this.userDao = userDao;
    }

    public int findUserIdByAuthInfo(AuthInfo authInfo) {
         Integer userId = userDao.selectByAuth(authInfo);
         if (userId == null) {
             throw new AuthorizationException("인가되지 않은 사용자 정보입니다.");
         }

         return userId.intValue();
    }
}
