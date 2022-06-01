package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dao.UserDao;
import woowacourse.auth.domain.User;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.exception.AuthenticationException;
import woowacourse.auth.support.JwtTokenProvider;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDao userDao;

    public AuthService(JwtTokenProvider jwtTokenProvider, UserDao userDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDao = userDao;
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        User user = findValidUser(tokenRequest.getUsername(), tokenRequest.getPassword());
        String accessToken = jwtTokenProvider.createToken(user.getTokenPayload());
        return new TokenResponse(accessToken);
    }

    private User findValidUser(String username, String password) {
        User user = userDao.findByUserName(username)
                .orElseThrow(AuthenticationException::new);
        if (!user.hasSamePassword(password)) {
            throw new AuthenticationException();
        }
        return user;
    }

    public Long findUserIdByToken(String token) {
        String payload = jwtTokenProvider.getPayload(token);
        return Long.valueOf(payload);
    }
}
