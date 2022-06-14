package woowacourse.auth.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.SignInRequest;
import woowacourse.auth.dto.SignInResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Email;
import woowacourse.shoppingcart.domain.Username;
import woowacourse.shoppingcart.exception.AuthorizationException;
import woowacourse.shoppingcart.exception.InvalidPasswordException;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private final CustomerDao customerDao;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthService(CustomerDao customerDao, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.customerDao = customerDao;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public SignInResponse signIn(SignInRequest signInRequest) {
        Customer customer = customerDao.findByEmail(new Email(signInRequest.getEmail()));
        validatePassword(signInRequest.getPassword(), customer.getPassword().getValue());
        String token = jwtTokenProvider.createToken(customer.getUsername().getValue());

        return SignInResponse.from(customer, token);
    }

    public SignInResponse reIssueToken(String username) {
        Customer customer = customerDao.findByUsername(new Username(username));
        String token = jwtTokenProvider.createToken(customer.getUsername().getValue());

        return SignInResponse.from(customer, token);
    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new InvalidPasswordException();
        }
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
