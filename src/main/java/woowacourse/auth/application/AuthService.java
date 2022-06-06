package woowacourse.auth.application;

import org.springframework.stereotype.Service;

import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.exception.NoSuchEmailException;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.exception.WrongPasswordException;
import woowacourse.shoppingcart.infrastructure.jdbc.dao.CustomerDao;

@Service
public class AuthService {

    private final CustomerDao customerDao;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(final CustomerDao customerDao, final JwtTokenProvider jwtTokenProvider) {
        this.customerDao = customerDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenResponse login(final TokenRequest tokenRequest) {
        final Customer customer = getCustomer(tokenRequest.getEmail());

        if (!customer.equalsPassword(tokenRequest.getPassword())) {
            throw new WrongPasswordException();
        }

        final String token = jwtTokenProvider.createToken(String.valueOf(customer.getId()));
        return new TokenResponse(customer.getNickname(), token);
    }

    private Customer getCustomer(final String email) {
        return customerDao.findByEmail(email)
                .orElseThrow(NoSuchEmailException::new);
    }
}
