package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.application.exception.InvalidTokenException;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.exception.InvalidCustomerException;
import woowacourse.shoppingcart.application.exception.NotMatchPasswordException;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.PasswordEncrypter;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncrypter passwordEncrypter;
    private final CustomerDao customerDao;

    public AuthService(final JwtTokenProvider jwtTokenProvider,
                       final PasswordEncrypter passwordEncrypter,
                       final CustomerDao customerDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncrypter = passwordEncrypter;
        this.customerDao = customerDao;
    }

    public Customer getAuthenticatedCustomer(final String token) {
        validateAvailableToken(token);
        Long id = Long.parseLong(jwtTokenProvider.getPayload(token));
        return customerDao.findById(id)
                .orElseThrow(InvalidCustomerException::new);
    }

    private void validateAvailableToken(final String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidTokenException();
        }
    }

    public TokenResponse login(final TokenRequest tokenRequest) {
        final Customer customer = customerDao.findByUserName(tokenRequest.getUserName())
                .orElseThrow(InvalidCustomerException::new);
        validatePassword(tokenRequest, customer);
        final String token = jwtTokenProvider.createToken(String.valueOf(customer.getId()));
        return new TokenResponse(token);
    }

    private void validatePassword(final TokenRequest tokenRequest, final Customer customer) {
        if (!passwordEncrypter.matches(tokenRequest.getPassword(), customer.getPassword())) {
            throw new NotMatchPasswordException();
        }
    }
}
