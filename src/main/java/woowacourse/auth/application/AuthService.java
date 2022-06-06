package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dao.CustomerDao;
import woowacourse.auth.dao.UserDao;
import woowacourse.auth.domain.Customer;
import woowacourse.auth.domain.Token;
import woowacourse.auth.domain.User;
import woowacourse.auth.dto.request.TokenRequest;
import woowacourse.auth.dto.response.TokenResponse;
import woowacourse.common.exception.AuthenticationException;

@Service
public class AuthService {

    private final CustomerDao customerDao;
    private final UserDao userDao;
    private final JwtTokenService tokenService;

    public AuthService(CustomerDao customerDao, UserDao userDao, JwtTokenService tokenService) {
        this.customerDao = customerDao;
        this.userDao = userDao;
        this.tokenService = tokenService;
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        User user = findValidUser(tokenRequest.getUsername(), tokenRequest.getPassword());
        Token accessToken = tokenService.generateToken(user.getTokenPayload());
        return new TokenResponse(accessToken.getValue());
    }

    private User findValidUser(String username, String password) {
        User user = userDao.findByUserName(username)
                .orElseThrow(AuthenticationException::ofLoginFailure);
        if (user.hasDifferentPassword(password)) {
            throw AuthenticationException.ofLoginFailure();
        }
        return user;
    }

    public Customer findCustomerByToken(String token) {
        String username = tokenService.extractPayload(new Token(token));
        return customerDao.findByUserName(username)
                .orElseThrow(AuthenticationException::ofInvalidToken);
    }
}
