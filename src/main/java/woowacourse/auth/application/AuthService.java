package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;

import woowacourse.shoppingcart.support.Encryptor;


@Service
@Transactional
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerDao customerDao;

    public AuthService(JwtTokenProvider jwtTokenProvider, CustomerDao customerDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerDao = customerDao;
    }

    public String createToken(final TokenRequest tokenRequest) {
        validateNameAndPassword(tokenRequest.getUserName(), tokenRequest.getPassword());
        return jwtTokenProvider.createToken(tokenRequest.getUserName());
    }

    private void validateNameAndPassword(final String name, final String password) {
        if (customerDao.existsCustomer(Customer.of(name, password))) {

            return;
        }
        throw new AuthorizationException("로그인에 실패했습니다.");
    }

    public void validateToken(String token) {
        if (token == null) {
            throw new AuthorizationException("토큰이 없습니다.");
        }

        if (jwtTokenProvider.isInvalidToken(token)) {
            throw new AuthorizationException("인증되지 않은 사용자입니다.");
        }
    }

    public String getPayload(String token) {
        return jwtTokenProvider.getPayload(token);
    }
}
