package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dao.UserDao;
import woowacourse.auth.domain.User;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.exception.AuthenticationException;

@Service
public class AuthService {

    private final JwtTokenService tokenService;
    private final UserDao userDao;

    public AuthService(JwtTokenService tokenService, UserDao userDao) {
        this.tokenService = tokenService;
        this.userDao = userDao;
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        User user = findValidUser(tokenRequest.getUsername(), tokenRequest.getPassword());
        String accessToken = tokenService.createToken(user.getTokenPayload());
        return new TokenResponse(accessToken);
    }

    private User findValidUser(String username, String password) {
        User user = findUser(username);
        if (!user.hasSamePassword(password)) {
            throw new AuthenticationException();
        }
        return user;
    }

    public User findUserByToken(String token) {
        String username = tokenService.getPayload(token);
        return findUser(username);
    }

    private User findUser(String username) {
        return userDao.findByUserName(username)
                .orElseThrow(AuthenticationException::new);
    }
}
