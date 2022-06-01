package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.support.JwtTokenProvider;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String createToken(final Long customerId) {
        return jwtTokenProvider.createToken(String.valueOf(customerId));
    }

    public boolean validateToken(final String token) {
        return jwtTokenProvider.validateToken(token);
    }

    public String getPayload(final String token) {
        return jwtTokenProvider.getPayload(token);
    }
}
