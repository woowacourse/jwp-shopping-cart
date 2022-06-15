package woowacourse.auth.service;

import java.util.Map;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import woowacourse.auth.domain.TokenProvider;
import woowacourse.auth.ui.dto.TokenRequest;
import woowacourse.exception.unauthorized.LoginFailureException;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.service.SpringCustomerService;

@Service
public class AuthService implements AuthenticationService {
    private final SpringCustomerService customerService;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthService(SpringCustomerService customerService, TokenProvider tokenProvider,
                       PasswordEncoder passwordEncoder) {
        this.customerService = customerService;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String getToken(TokenRequest tokenRequest) {
        final Customer customer = customerService.getByEmail(tokenRequest.getEmail());
        validatePassword(tokenRequest, customer);

        return tokenProvider.createToken(Map.of("id", customer.getId()));
    }

    private void validatePassword(TokenRequest tokenRequest, Customer customer) {
        if (!customer.validatePassword(tokenRequest.getPassword(), passwordEncoder)) {
            throw new LoginFailureException();
        }
    }
}
