package woowacourse.auth.application;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.InvalidLoginException;
import woowacourse.shoppingcart.exception.NotFoundCustomerException;

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
            checkPassword(request, customer);
            return jwtTokenProvider.createToken(request.getEmail());
        } catch (NotFoundCustomerException exception) {
            throw new InvalidLoginException();
        }
    }

    private void checkPassword(TokenRequest request, Customer customer) {
        if (!BCrypt.checkpw(request.getPassword(), customer.getPassword())) {
            throw new InvalidLoginException();
        }
    }
}
