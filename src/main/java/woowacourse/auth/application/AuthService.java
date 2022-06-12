package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.domain.SignIn;
import woowacourse.auth.dto.SignInRequest;
import woowacourse.auth.dto.SignInResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerRepository;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.AuthorizationException;
import woowacourse.shoppingcart.exception.InvalidPasswordException;
import woowacourse.shoppingcart.exception.NoSuchCustomerException;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerRepository customerDao;

    public AuthService(JwtTokenProvider jwtTokenProvider, CustomerRepository customerDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerDao = customerDao;
    }

    @Transactional
    public SignInResponse createToken(String username) {
        Customer customer = customerDao.findByUsername(username);
        return SignInResponse.fromCustomer(customer, jwtTokenProvider);
    }

    @Transactional(readOnly = true)
    public SignInResponse signIn(SignInRequest signInRequest) {
        SignIn signIn = signInRequest.toSignIn();
        validateSignIn(signIn);

        Customer customer = customerDao.findByEmail(signIn.getEmail());

        return SignInResponse.fromCustomer(customer, jwtTokenProvider);
    }

    private void validateSignIn(SignIn signIn) {
        try {
            validateExistEmail(signIn.getEmail());
            validatePassword(signIn.getEmail(), signIn.getPassword());
        } catch (NoSuchCustomerException | InvalidPasswordException e) {
            throw new AuthorizationException("로그인에 실패했습니다.");
        }
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
