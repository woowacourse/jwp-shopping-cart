package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;

@Service
public class AuthService {

    private static final String USERNAME = "유효한_아이디";
    private static final String PASSWORD = "비밀번호";

    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        String username = tokenRequest.getUsername();
        String password = tokenRequest.getPassword();
        if (checkInvalidLogin(username, password)) {
            throw new AuthorizationException();
        }
        String accessToken = jwtTokenProvider.createToken("1");
        return new TokenResponse(accessToken);
    }

    public Long findUserIdByToken(String token) {
        String payload = jwtTokenProvider.getPayload(token);
        return Long.valueOf(payload);
    }

    public boolean checkInvalidLogin(String principal, String credentials) {
        return !USERNAME.equals(principal) || !PASSWORD.equals(credentials);
    }
}
