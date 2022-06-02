package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.exception.auth.LoginFailureException;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.application.PasswordEncoderAdapter;
import woowacourse.shoppingcart.domain.Customer;

@Service
public class AuthService {
    private final CustomerService customerService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(CustomerService customerService, JwtTokenProvider jwtTokenProvider) {
        this.customerService = customerService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenResponse getToken(TokenRequest tokenRequest) {
        final Customer customer = customerService.getIdByEmail(tokenRequest.getEmail());
        validatePassword(tokenRequest, customer);

        final String accessToken = jwtTokenProvider.createToken(customer.getId());
        return new TokenResponse(accessToken);
    }

    private void validatePassword(TokenRequest tokenRequest, Customer customer) {
        if (!customer.validatePassword(tokenRequest.getPassword(), new PasswordEncoderAdapter())) {
            throw new LoginFailureException();
        }
    }
}
