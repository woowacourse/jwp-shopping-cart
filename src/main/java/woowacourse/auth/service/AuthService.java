package woowacourse.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.auth.ui.dto.TokenRequest;
import woowacourse.auth.ui.dto.TokenResponse;
import woowacourse.exception.LoginFailureException;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.service.CustomerService;

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
        final Customer customer = customerService.getByEmail(tokenRequest.getEmail());
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
