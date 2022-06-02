package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dao.UserDao;
import woowacourse.auth.domain.Token;
import woowacourse.auth.domain.User;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.exception.AuthenticationException;
import woowacourse.auth.support.JwtPropertySource;

@Service
public class AuthService {

    private final UserDao userDao;
    private final JwtPropertySource jwtPropertySource;

    public AuthService(UserDao userDao, JwtPropertySource jwtPropertySource) {
        this.userDao = userDao;
        this.jwtPropertySource = jwtPropertySource;
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        User user = findValidUser(tokenRequest.getUsername(), tokenRequest.getPassword());
        Token accessToken = Token.of(user.getTokenPayload(), jwtPropertySource);
        return new TokenResponse(accessToken.getValue());
    }

    private User findValidUser(String username, String password) {
        User user = findUser(username);
        if (user.hasDifferentPassword(password)) {
            throw new AuthenticationException();
        }
        return user;
    }

    public User findUserByToken(String token) {
        Token accessToken = new Token(token, jwtPropertySource.getSecretKey());
        String username = accessToken.extractPayload();
        return findUser(username);
    }

    private User findUser(String username) {
        return userDao.findByUserName(username)
                .orElseThrow(AuthenticationException::new);
    }
}
