package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.LonginRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenResponse createToken(LonginRequest request) {
        return new TokenResponse(jwtTokenProvider.createToken(request.getEmail()));
    }
}
