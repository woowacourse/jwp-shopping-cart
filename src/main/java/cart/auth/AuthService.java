package cart.auth;

import cart.dao.UserDao;
import cart.dao.entity.User;
import cart.dto.AuthUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private final BasicAuthExtractor basicAuthExtractor;
    private final UserDao userDao;

    public AuthService(BasicAuthExtractor basicAuthExtractor, UserDao userDao) {
        this.basicAuthExtractor = basicAuthExtractor;
        this.userDao = userDao;
    }

    public AuthUser findAuthUser(final AuthHeader header) {
        final AuthInfo authInfo = basicAuthExtractor.extract(header);

        return findByAuthPrincipal(authInfo);
    }

    private AuthUser findByAuthPrincipal(final AuthInfo authInfo) {
        final User findUser = userDao.findByEmail(authInfo.getEmail())
                .orElseThrow(() -> new NoSuchElementException("해당 사용자를 찾을 수 없습니다."));

        findUser.validatePassword(authInfo.getPassword());

        return new AuthUser(findUser);
    }
}
