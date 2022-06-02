package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.domain.Customer;
import woowacourse.auth.dto.token.TokenRequest;
import woowacourse.auth.dto.token.TokenResponse;
import woowacourse.auth.exception.InvalidAuthException;
import woowacourse.auth.support.JwtTokenProvider;

@Service
public class AuthService {

    private final CustomerService customerService;
    private final JwtTokenProvider tokenProvider;

    public AuthService(CustomerService customerService, JwtTokenProvider tokenProvider) {
        this.customerService = customerService;
        this.tokenProvider = tokenProvider;
    }

    public TokenResponse login(TokenRequest tokenRequest) {
        Customer customer = customerService.findByEmail(tokenRequest.getEmail());
        if (!customer.isSamePassword(tokenRequest.getPassword())) {
            throw new InvalidAuthException("비밀번호가 일치하지 않습니다.");
        }
        return new TokenResponse(customer.getNickname(), tokenProvider.createToken(customer.getEmail()));
    }
}
