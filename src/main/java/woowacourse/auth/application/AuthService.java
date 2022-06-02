package woowacourse.auth.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.exception.InvalidTokenException;
import woowacourse.auth.exception.NotMatchPasswordException;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final CustomerDao customerDao;

    public AuthService(final JwtTokenProvider jwtTokenProvider,
                       final PasswordEncoder passwordEncoder, final CustomerDao customerDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.customerDao = customerDao;
    }

    public Customer getAuthenticatedCustomer(final String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidTokenException();
        }
        Long id = Long.parseLong(jwtTokenProvider.getPayload(token));
        return customerDao.findById(id)
                .orElseThrow(InvalidCustomerException::new);
    }

    public TokenResponse login(final TokenRequest tokenRequest) {
        final Customer customer = customerDao.findByUserName(tokenRequest.getUserName())
                .orElseThrow(InvalidCustomerException::new);
        validatePassword(tokenRequest, customer);
        final String token = jwtTokenProvider.createToken(String.valueOf(customer.getId()));
        return new TokenResponse(token);
    }

    private void validatePassword(final TokenRequest tokenRequest, final Customer customer) {
        if (!passwordEncoder.matches(tokenRequest.getPassword(), customer.getPassword())) {
            throw new NotMatchPasswordException();
        }
    }
}
