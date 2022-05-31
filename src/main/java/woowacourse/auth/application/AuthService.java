package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.application.dto.TokenCreateRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerDao customerDao;

    public AuthService(JwtTokenProvider jwtTokenProvider, CustomerDao customerDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerDao = customerDao;
    }

    public TokenResponse createToken(TokenCreateRequest tokenCreateRequest) {
        Customer customer = customerDao.findByEmailAndPassword(tokenCreateRequest.getEmail(), tokenCreateRequest.getPassword())
                .orElseThrow(() -> new IllegalArgumentException("로그인 정보가 일치하지 않습니다."));

        String accessToken = jwtTokenProvider.createToken(String.valueOf(customer.getId()));
        return new TokenResponse(accessToken);
    }
}
