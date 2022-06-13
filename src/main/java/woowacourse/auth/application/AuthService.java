package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.exception.LoginFailureException;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;

@Service
@Transactional
public class AuthService {
    private final CustomerDao customerDao;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(CustomerDao customerDao, JwtTokenProvider jwtTokenProvider) {
        this.customerDao = customerDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional(readOnly = true)
    public TokenResponse getToken(TokenRequest tokenRequest) {
        final Customer customer = customerDao.findByEmail(tokenRequest.getEmail())
                .orElseThrow(LoginFailureException::new);

        if (!customer.isCorrectPassword(tokenRequest.getPassword())) {
            throw new LoginFailureException();
        }
        final String accessToken = jwtTokenProvider.createToken(customer.getId());
        return new TokenResponse(accessToken);
    }
}
