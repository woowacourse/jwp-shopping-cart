package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.CustomerService;

@Service
public class AuthService {

    private final CustomerService customerService;
    private final JwtTokenProvider tokenProvider;

    public AuthService(CustomerService customerService, JwtTokenProvider tokenProvider) {
        this.customerService = customerService;
        this.tokenProvider = tokenProvider;
    }

    public TokenResponse login(TokenRequest tokenRequest) {
        String token = tokenProvider.createToken(tokenRequest.getEmail());
        return new TokenResponse(token);
    }
}
