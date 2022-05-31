package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.exception.InvalidTokenException;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenResponse createToken(Long id) {
        return new TokenResponse(jwtTokenProvider.createToken(String.valueOf(id)));
    }

    public Long extractIdFromRequest(HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);

        if(token == null){
            throw new InvalidTokenException("유효하지 않은 토큰입니다.");
        }

        return Long.parseLong(jwtTokenProvider.getPayload(token));
    }
}
