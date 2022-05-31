package woowacourse.auth.application;

import org.springframework.stereotype.Service;

import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.domain.customer.Customer;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerService customerService;

    public AuthService(JwtTokenProvider jwtTokenProvider,
        CustomerService customerService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerService = customerService;
    }

    public TokenResponse createToken(LoginRequest loginRequest) {
        String accessToken = jwtTokenProvider.createToken(loginRequest.getUsername());
        return new TokenResponse(accessToken);
    }

    public Customer findCustomerByUsername(String username) {
        return customerService.findByUsername(username);
    }
}
