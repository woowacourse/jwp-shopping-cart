package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.CustomerService;

@Service
public class AuthService {
    private final CustomerService customerService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(JwtTokenProvider jwtTokenProvider, CustomerService customerService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerService = customerService;
    }

    public String createToken(final TokenRequest tokenRequest) {
        customerService.validateNameAndPassword(tokenRequest.getUserName(), tokenRequest.getPassword());
        return jwtTokenProvider.createToken(tokenRequest.getUserName());
    }

    public boolean isInvalidToken(String token) {
        return !jwtTokenProvider.validateToken(token);
    }

    public String getNameFromToken(String token) {
        return jwtTokenProvider.getPayload(token);
    }
}
