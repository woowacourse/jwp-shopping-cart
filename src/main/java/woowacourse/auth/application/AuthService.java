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

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final CustomerDao customerDao;

    public AuthService(JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, CustomerDao customerDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.customerDao = customerDao;
    }

    public SignInResponse signIn(SignInRequest signInRequest) {
        Customer customer = customerDao.findByEmail(new Email(signInRequest.getEmail()));
        validatePassword(signInRequest.getPassword(), customer.getPassword().getValue());

        return new SignInResponse(
                customer.getUsername().getValue(),
                customer.getEmail().getValue(),
                jwtTokenProvider.createToken(customer.getUsername().getValue())
        );
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
