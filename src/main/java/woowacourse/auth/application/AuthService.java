package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.support.JwtTokenProvider;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String createToken(final TokenRequest tokenRequest) {
        return jwtTokenProvider.createToken(tokenRequest.getUserName());
    }

    public boolean isInvalidToken(String token) {
        return !jwtTokenProvider.validateToken(token);
    }

    public String getNameFromToken(String token) {
        return jwtTokenProvider.getPayload(token);
    }
}
