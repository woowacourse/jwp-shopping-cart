package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dao.UserDao;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.domain.User;

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
                .orElseThrow(AuthorizationException::new);
        if (!user.hasSamePassword(password)) {
            throw new AuthorizationException();
        }
        return user;
    }

    public Long findUserIdByToken(String token) {
        String payload = jwtTokenProvider.getPayload(token);
        return Long.valueOf(payload);
    }
}
