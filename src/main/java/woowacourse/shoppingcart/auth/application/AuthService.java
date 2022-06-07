package woowacourse.shoppingcart.auth.application;

import org.springframework.stereotype.Service;

import woowacourse.shoppingcart.auth.dto.TokenRequest;
import woowacourse.shoppingcart.auth.dto.TokenResponse;
import woowacourse.shoppingcart.auth.exception.NoSuchEmailException;
import woowacourse.shoppingcart.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.customer.support.jdbc.dao.CustomerDao;
import woowacourse.shoppingcart.exception.WrongPasswordException;

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
