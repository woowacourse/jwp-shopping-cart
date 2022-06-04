package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.domain.SignIn;
import woowacourse.auth.dto.SignInRequest;
import woowacourse.auth.dto.SignInResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.InvalidPasswordException;
import woowacourse.shoppingcart.exception.NoSuchCustomerException;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerDao customerDao;

    public AuthService(JwtTokenProvider jwtTokenProvider, CustomerDao customerDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerDao = customerDao;
    }

    @Transactional(readOnly = true)
    public SignInResponse signIn(SignInRequest signInRequest) {
        SignIn signIn = signInRequest.toSignIn();
        validateExistEmail(signIn.getEmail());
        validatePassword(signIn.getEmail(), signIn.getPassword());

        Customer customer = customerDao.findByEmail(signIn.getEmail());

        return SignInResponse.fromCustomer(customer, jwtTokenProvider);
    }

    private void validateExistEmail(String email) {
        if (!customerDao.existByEmail(email)) {
            throw new NoSuchCustomerException();
        }
    }

    private void validatePassword(String email, String password) {
        if (!customerDao.isValidPasswordByEmail(email, password)) {
            throw new InvalidPasswordException();
        }
    }
}
