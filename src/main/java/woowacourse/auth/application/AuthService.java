package woowacourse.auth.application;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.exception.ForbiddenException;
import woowacourse.auth.exception.LoginFailedException;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Password;
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

    public void validateToken(String accessToken, String customerId) {
        jwtTokenProvider.validateToken(accessToken);
        String payload = jwtTokenProvider.getPayload(accessToken);

        if (!payload.equals(customerId)) {
            throw new ForbiddenException();
        }
    }

    private void validateEmailExisting(String email) {
        if (!customerDao.hasEmail(email)) {
            throw new LoginFailedException();
        }
    }

    private void validatePassword(String password, CustomerEntity customerEntity) {
        Password encryptedPassword = new Password(customerEntity.getPassword(), new BCryptPasswordEncoder());
        boolean isValid = encryptedPassword.matches(password);

        if (!isValid) {
            throw new LoginFailedException();
        }
    }
}
