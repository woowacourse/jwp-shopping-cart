package woowacourse.shoppingcart.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.auth.application.dto.request.TokenRequest;
import woowacourse.shoppingcart.auth.application.dto.response.TokenResponse;
import woowacourse.shoppingcart.auth.support.jwt.JwtTokenProvider;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.customer.support.exception.CustomerException;
import woowacourse.shoppingcart.customer.support.exception.CustomerExceptionCode;
import woowacourse.shoppingcart.customer.support.jdbc.dao.CustomerDao;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private final CustomerDao customerDao;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(final CustomerDao customerDao, final JwtTokenProvider jwtTokenProvider) {
        this.customerDao = customerDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenResponse login(final TokenRequest tokenRequest) {
        final Customer customer = getCustomer(tokenRequest.getEmail());

        if (customer.isPasswordDisMatch(tokenRequest.getPassword())) {
                throw new CustomerException(CustomerExceptionCode.MISMATCH_EMAIL_OR_PASSWORD);
        }

        final String token = jwtTokenProvider.createToken(String.valueOf(customer.getId()));
        return new TokenResponse(customer.getNickname(), token);
    }

    private Customer getCustomer(final String email) {
        return customerDao.findByEmail(email)
                .orElseThrow(() -> new CustomerException(CustomerExceptionCode.MISMATCH_EMAIL_OR_PASSWORD));
    }
}
