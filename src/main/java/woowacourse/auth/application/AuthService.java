package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public boolean isValid(String header) {
        String token = AuthorizationExtractor.extract(header);
        jwtTokenProvider.validateToken(token);
        return true;
    }
}
