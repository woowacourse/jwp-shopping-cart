package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional(readOnly = true)
    public Customer getAuthenticatedCustomer(final String token) {
        Long id = Long.parseLong(jwtTokenProvider.getPayload(token));
        return customerDao.findById(id)
                .orElseThrow(InvalidCustomerException::new);
    }

    @Transactional
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
