package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.application.dto.TokenCreateRequest;
import woowacourse.auth.application.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.global.exception.InvalidTokenException;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;

@Transactional(readOnly = true)
@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerDao customerDao;

    public AuthService(final JwtTokenProvider jwtTokenProvider, final CustomerDao customerDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerDao = customerDao;
    }

    public TokenResponse createToken(final TokenCreateRequest tokenCreateRequest) {
        Customer customer = customerDao.findByEmailAndPassword(tokenCreateRequest.getEmail(), tokenCreateRequest.getPassword())
                .orElseThrow(() -> new InvalidTokenException("로그인 정보가 일치하지 않습니다."));

        String accessToken = jwtTokenProvider.createToken(String.valueOf(customer.getId()));
        return new TokenResponse(accessToken);
    }

    @Override
    public String toString() {
        return "AuthService{" +
                "jwtTokenProvider=" + jwtTokenProvider +
                ", customerDao=" + customerDao +
                '}';
    }
}
