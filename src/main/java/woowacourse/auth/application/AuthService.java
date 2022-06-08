package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.exception.bodyexception.InvalidLoginException;

@Service
public class AuthService {

    private final CustomerService customerService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(CustomerService customerService, JwtTokenProvider jwtTokenProvider) {
        this.customerService = customerService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String login(TokenRequest request) {
        try {
            Customer customer = customerService.getByEmail(request.getEmail());
            customer.checkPassword(request.getPassword());
            return jwtTokenProvider.createToken(request.getEmail());
        } catch (IllegalArgumentException exception) {
            throw new InvalidLoginException();
        }
    }
}
