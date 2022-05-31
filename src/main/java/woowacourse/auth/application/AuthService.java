package woowacourse.auth.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.exception.auth.LoginFailureException;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.domain.Customer;

@Service
public class AuthService {
    private final CustomerService customerService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthService(CustomerService customerService, JwtTokenProvider jwtTokenProvider,
                       PasswordEncoder passwordEncoder) {
        this.customerService = customerService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public TokenResponse getToken(TokenRequest tokenRequest) {
        final Customer customer = customerService.getIdByEmail(tokenRequest.getEmail());
        validatePassword(tokenRequest, customer);

        final String accessToken = jwtTokenProvider.createToken(customer.getId());
        return new TokenResponse(accessToken);
    }

    private void validatePassword(TokenRequest tokenRequest, Customer customer) {
        if (!customer.validatePassword(tokenRequest.getPassword(), passwordEncoder)) {
            throw new LoginFailureException();
        }
    }
}
