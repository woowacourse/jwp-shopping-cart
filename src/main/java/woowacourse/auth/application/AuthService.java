package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.SignInRequest;
import woowacourse.shoppingcart.dto.SignInResponse;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
public class AuthService {

    private static final String DIFFERENT_PASSWORD = "[ERROR] 비밀번호가 일치하지 않습니다.";

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerDao customerDao;

    public AuthService(final JwtTokenProvider jwtTokenProvider, final CustomerDao customerDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerDao = customerDao;
    }

    public SignInResponse signIn(final SignInRequest signInRequest) {
        final var email = signInRequest.getEmail();
        final var authorizedCustomer = customerDao.findCustomerByEmail(email);
        final var customer = authorizedCustomer.toCustomer();

        final var username = customer.getUsername();

        validateSamePassword(signInRequest, customer);

        final var token = jwtTokenProvider.createToken(username);

        return new SignInResponse(username, email, token);
    }

    private void validateSamePassword(final SignInRequest signInRequest, final Customer customer) {
        final var password = signInRequest.toPassword();

        if (!customer.isSamePassword(password)) {
            throw new InvalidCustomerException(DIFFERENT_PASSWORD);
        }
    }

    public String decode(final String token) {
        return jwtTokenProvider.getPayload(token);
    }

    public boolean isValidToken(final String token) {
        return jwtTokenProvider.validateToken(token);
    }
}
