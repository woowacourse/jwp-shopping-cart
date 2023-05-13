package cart.auth;

import cart.dao.UserDao;
import cart.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private static final String INVALID_USER_INFO = "잘못된 유저 정보입니다.";

    private final UserDao userDao;

    public AuthService(final UserDao userDao) {
        this.userDao = userDao;
    }

    public void validate(final UserInfo userInfo) throws AuthenticationException {
        final User user = userDao.findByEmail(userInfo.getEmail())
                .orElseThrow(() -> new AuthenticationException(INVALID_USER_INFO));

        if (userInfo.notEquals(user)) {
            throw new AuthenticationException(INVALID_USER_INFO);
        }
    }
}
