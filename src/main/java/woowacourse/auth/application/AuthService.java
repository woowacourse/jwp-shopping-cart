package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.CustomerService;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerService customerService;

    public AuthService(JwtTokenProvider jwtTokenProvider, CustomerService customerService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerService = customerService;
    }

    public String createToken(final TokenRequest tokenRequest) {
        Long memberId = customerService.findIdByNameAndPassword(tokenRequest.getUserName(), tokenRequest.getPassword());
        return jwtTokenProvider.createToken(String.valueOf(memberId));
    }

    public boolean isInvalidToken(String token) {
        return !jwtTokenProvider.validateToken(token);
    }

    public Long findLoginMemberId(String token) {
        if (isInvalidToken(token)) {
            throw new AuthorizationException();
        }
        String memberId = jwtTokenProvider.getPayload(token);
        return Long.valueOf(memberId);
    }
}
