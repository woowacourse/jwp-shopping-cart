package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.application.dto.TokenCreateRequest;
import woowacourse.auth.application.dto.TokenResponse;
import woowacourse.auth.support.Encryption;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;

@Transactional(readOnly = true)
@Service
public class AuthService {

    private final Encryption encryption;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerDao customerDao;

    public AuthService(Encryption encryption, JwtTokenProvider jwtTokenProvider, CustomerDao customerDao) {
        this.encryption = encryption;
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerDao = customerDao;
    }

    public TokenResponse createToken(TokenCreateRequest tokenCreateRequest) {
        Customer customer = customerDao.findByEmail(tokenCreateRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 사용자 입니다."));
        validateSamePassword(customer.getPassword(), tokenCreateRequest.getPassword());

        String accessToken = jwtTokenProvider.createToken(String.valueOf(customer.getId()));
        return new TokenResponse(accessToken);
    }

    private void validateSamePassword(String password, String requestPassword) {
        if (encryption.bytesToHex(password.getBytes()).equals(requestPassword)) {
            throw new IllegalArgumentException("[ERROR] 비밀번호가 일치하지 않습니다.");
        }
    }
}
