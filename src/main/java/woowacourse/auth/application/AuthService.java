package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.SignInRequest;
import woowacourse.auth.dto.SignInResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Email;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.domain.Username;
import woowacourse.shoppingcart.exception.AuthorizationException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerDao customerDao;

    public AuthService(JwtTokenProvider jwtTokenProvider, CustomerDao customerDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerDao = customerDao;
    }

    public SignInResponse signIn(SignInRequest signInRequest) {
        Customer customer = customerDao.findByEmail(new Email(signInRequest.getEmail()));
        Password password = new Password(signInRequest.getPassword());
        customer.validatePassword(password);

        return new SignInResponse(
                customer.getUsername().getValue(),
                customer.getEmail().getValue(),
                jwtTokenProvider.createToken(customer.getUsername().getValue())
        );
    }

    public void validateExistUser(String username) {
        if (!customerDao.existByUsername(new Username(username))) {
            throw new AuthorizationException();
        }
    }

    public void validateToken(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new AuthorizationException();
        }
    }
}
