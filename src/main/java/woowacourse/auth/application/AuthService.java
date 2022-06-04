package woowacourse.auth.application;

import java.util.Optional;
import org.springframework.stereotype.Service;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
public class AuthService {

    private final CustomerDao customerDao;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(CustomerDao customerDao, JwtTokenProvider jwtTokenProvider) {
        this.customerDao = customerDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenResponse loginCustomer(TokenRequest tokenRequest) {
        Long customerId = findCustomerId(tokenRequest);
        return createToken(customerId);
    }

    private Long findCustomerId(TokenRequest tokenRequest) {
        Password encryptPassword = Password.encrypt(tokenRequest.getPassword());
        Optional<Long> idByEmailAndPassword = customerDao.findIdByEmailAndPassword(tokenRequest.getEmail(),
                encryptPassword.getPassword());
        return idByEmailAndPassword.orElseThrow(
                () -> new InvalidCustomerException("Email 또는 Password가 일치하지 않습니다."));
    }

    private TokenResponse createToken(Long customerId) {
        String accessToken = jwtTokenProvider.createToken(String.valueOf(customerId));
        return new TokenResponse(accessToken);
    }

}
