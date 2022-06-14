package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.exception.LoginFailException;
import woowacourse.auth.exception.NoCustomerTokenException;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.auth.support.PasswordEncoder;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.EncodedPassword;
import woowacourse.shoppingcart.domain.customer.UnEncodedPassword;
import woowacourse.shoppingcart.dto.customer.CustomerResponse;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(JwtTokenProvider jwtTokenProvider,
                       CustomerService customerService,
                       PasswordEncoder passwordEncoder) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerService = customerService;
        this.passwordEncoder = passwordEncoder;
    }

    public TokenResponse createToken(TokenRequest request) {
        EncodedPassword encodedPassword = passwordEncoder.encode(new UnEncodedPassword(request.getPassword()));
        Customer loginCustomer = fetchUser(request, encodedPassword.getValue());
        String token = jwtTokenProvider.createToken(loginCustomer.getEmail());

        return new TokenResponse(token, jwtTokenProvider.getValidityInMilliseconds(),
                new CustomerResponse(loginCustomer));
    }

    private Customer fetchUser(TokenRequest request, String encodedPassword) {
        try {
            return customerService.getByEmailAndPassword(request.getEmail(), encodedPassword);
        } catch (InvalidCustomerException exception) {
            throw new LoginFailException();
        }
    }

    public Customer findCustomerByToken(String token) {
        String email = jwtTokenProvider.getPayload(token);
        return customerService.getByEmail(email);
    }

    public void validateExistCustomerByToken(String token) {
        String email = jwtTokenProvider.getPayload(token);
        try {
            customerService.getByEmail(email);
        } catch (InvalidCustomerException customerException) {
            throw new NoCustomerTokenException();
        }
    }
}
