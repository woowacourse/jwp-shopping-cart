package cart.auth;

import cart.auth.excpetion.AuthorizeException;
import cart.dao.UserDao;
import cart.dao.entity.User;
import cart.dto.AuthUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private static final String DIFFERENT_AUTHORIZATION = "인증 정보가 다릅니다.";
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
                .orElseThrow(() -> new AuthorizeException(DIFFERENT_AUTHORIZATION));

        if (findUser.isDifferentPassword(authInfo.getPassword())) {
            throw new AuthorizeException(DIFFERENT_AUTHORIZATION);
        }

        return new AuthUser(findUser);
    }
}
