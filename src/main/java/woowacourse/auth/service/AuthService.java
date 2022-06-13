package woowacourse.auth.service;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.exception.authentication.LoginFailedException;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Password;
import woowacourse.shoppingcart.repository.CustomerRepository;

@Service
public class AuthService {
    private final CustomerRepository customerRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(CustomerRepository customerRepository, JwtTokenProvider jwtTokenProvider) {
        this.customerRepository = customerRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void validateToken(String accessToken) {
        jwtTokenProvider.validateToken(accessToken);
    }

    public TokenResponse generateToken(TokenRequest tokenRequest) {
        String email = tokenRequest.getEmail();
        String password = tokenRequest.getPassword();
        validateEmailExisting(email);

        Customer customer = customerRepository.findByEmail(email);
        validatePassword(password, customer);

        long customerId = customer.getId();
        String token = jwtTokenProvider.createToken(String.valueOf(customerId));

        return new TokenResponse(token, customerId);
    }

    private void validateEmailExisting(String email) {
        if (!customerRepository.existsByEmail(email)) {
            throw new LoginFailedException();
        }
    }

    private void validatePassword(String password, Customer customer) {
        Password cipherPassword = customer.getPassword();
        boolean isValid = cipherPassword.matches(password);

        if (!isValid) {
            throw new LoginFailedException();
        }
    }
}
