package woowacourse.auth.application;

import java.util.Optional;

import org.springframework.stereotype.Service;

import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerDao customerDao;

    public AuthService(JwtTokenProvider jwtTokenProvider, CustomerDao customerDao) {
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
        if (!customer.getPassword().equals(password)) {
            throw new InvalidCustomerException();
        }
    }
}
