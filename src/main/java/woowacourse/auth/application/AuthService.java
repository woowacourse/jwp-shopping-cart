package woowacourse.auth.application;

import java.util.Map;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerMapping;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.exception.LoginFailedException;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.password.Password;
import woowacourse.shoppingcart.domain.customer.password.PasswordEncoderAdapter;
import woowacourse.shoppingcart.entity.CustomerEntity;

@Service
public class AuthService {
    private final CustomerDao customerDao;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(CustomerDao customerDao, JwtTokenProvider jwtTokenProvider) {
        this.customerDao = customerDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenResponse generateToken(TokenRequest tokenRequest) {
        String email = tokenRequest.getEmail();
        String password = tokenRequest.getPassword();
        validateEmailExisting(email);

        CustomerEntity customerEntity = customerDao.findByEmail(email);
        validatePassword(password, customerEntity);

        int customerId = customerEntity.getId();
        String token = jwtTokenProvider.createToken(String.valueOf(customerId));

        return new TokenResponse(token, customerId);
    }

    private void validateEmailExisting(String email) {
        if (!customerDao.hasEmail(email)) {
            throw new LoginFailedException();
        }
    }

    private void validatePassword(String password, CustomerEntity customerEntity) {
        Password encryptedPassword = new Password(customerEntity.getPassword(), new PasswordEncoderAdapter());

        if (!encryptedPassword.matches(password)) {
            throw new LoginFailedException();
        }
    }

    public int getCustomerId(String token) {
        final String accessToken = AuthorizationExtractor.extract(token);

        validateToken(accessToken);

        final int customerId = Integer.parseInt(jwtTokenProvider.getPayload(accessToken));
        validateCustomerId(customerId);

        return customerId;
    }

    private void validateToken(String accessToken) {
        jwtTokenProvider.validateToken(accessToken);
    }

    private void validateCustomerId(int customerId) {
        customerDao.findById(customerId);
    }
}
