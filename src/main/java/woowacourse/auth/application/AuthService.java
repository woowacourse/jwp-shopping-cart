package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.exception.InvalidTokenException;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerDao customerDao;

    public AuthService(final JwtTokenProvider jwtTokenProvider, final CustomerDao customerDao) {
        this.jwtTokenProvider = jwtTokenProvider;
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
        customer.validatePassword(tokenRequest.getPassword());

        final String token = jwtTokenProvider.createToken(String.valueOf(customer.getId()));
        return new TokenResponse(token);
    }
}
