package woowacourse.auth.application;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.exception.LoginFailException;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerService customerService;

    public String createToken(TokenRequest request) {
        try {
            Customer loginCustomer = customerService.findByEmailAndPassword(request.getEmail(), request.getPassword());
            return jwtTokenProvider.createToken(loginCustomer.getEmail());
        } catch (InvalidCustomerException exception) {
            throw new LoginFailException();
        }
    }

    public Customer findCustomerByToken(String token) {
        String email = jwtTokenProvider.getPayload(token);
        return customerService.findByEmail(email);
    }
}
