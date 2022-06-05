package woowacourse.auth.application;

import java.util.Optional;

import org.springframework.stereotype.Service;

import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.password.EncodedPassword;
import woowacourse.shoppingcart.domain.customer.password.PasswordEncoder;
import woowacourse.shoppingcart.domain.customer.password.RawPassword;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerDao customerDao;

    public AuthService(PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider, CustomerDao customerDao) {
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerDao = customerDao;
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        String username = tokenRequest.getUsername();
        String password = tokenRequest.getPassword();

        validateCustomer(username, password);

        String token = jwtTokenProvider.createToken(username);
        return new TokenResponse(token);
    }

    private void validateCustomer(String username, String password) {
        Optional<Customer> optionalCustomer = customerDao.findByUsername(username);
        if (optionalCustomer.isEmpty()) {
            throw new InvalidCustomerException();
        }
        Customer customer = optionalCustomer.get();
        RawPassword rawPassword = new RawPassword(password);
        EncodedPassword encodedPassword = customer.getPassword();
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new InvalidCustomerException();
        }
    }
}
