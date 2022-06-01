package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dao.UserDao2;
import woowacourse.auth.domain.User2;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.exception.AuthorizationException;
import woowacourse.auth.support.JwtTokenProvider;

@Service
public class AuthService2 {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDao2 userDao;

    public AuthService2(JwtTokenProvider jwtTokenProvider, UserDao2 userDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDao = userDao;
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        User2 user = findValidUser(tokenRequest.getUsername(), tokenRequest.getPassword());
        String accessToken = jwtTokenProvider.createToken(user.getTokenPayload());
        return new TokenResponse(accessToken);
    }

    private User2 findValidUser(String username, String password) {
        User2 user = findUser(username);
        if (!user.hasSamePassword(password)) {
            throw new AuthorizationException();
        }
        return user;
    }

    public User2 findUserByToken(String token) {
        String username = jwtTokenProvider.getPayload(token);
        return findUser(username);
    }

    private User2 findUser(String username) {
        return userDao.findByUserName(username)
                .orElseThrow(AuthorizationException::new);
    }
}
