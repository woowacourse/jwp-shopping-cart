package woowacourse.auth.application;

import org.springframework.stereotype.Service;

import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.dto.LoginTokenResponse;
import woowacourse.auth.exception.LoginFailException;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerService customerService;

    public AuthService(JwtTokenProvider jwtTokenProvider,
        CustomerService customerService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerService = customerService;
    }

    public LoginTokenResponse createToken(LoginRequest request) {
        try {
            Customer loginCustomer = customerService.findByEmailAndPassword(request.getEmail(), request.getPassword());
            String token = jwtTokenProvider.createToken(loginCustomer.getEmail());
            return new LoginTokenResponse(token, jwtTokenProvider.getValidityInMilliseconds(),
                loginCustomer);
        } catch (InvalidCustomerException exception) {
            throw new LoginFailException();
        }
    }

    public Customer findCustomerByToken(String token) {
        String email = jwtTokenProvider.getPayload(token);
        return customerService.findByEmail(email);
    }
}
