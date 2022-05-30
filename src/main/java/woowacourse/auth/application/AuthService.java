package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.LoginFailException;

@Service
public class AuthService {

    private final CustomerDao customerDao;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(CustomerDao customerDao, JwtTokenProvider jwtTokenProvider) {
        this.customerDao = customerDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenResponse createToken(final TokenRequest tokenRequest) {
        final Customer customer = customerDao.findByAccount(tokenRequest.getAccount())
                .orElseThrow(LoginFailException::new);

        if (!customer.checkPassword(tokenRequest.getPassword())) {
            throw new LoginFailException();
        }

        return new TokenResponse(jwtTokenProvider.createToken(String.valueOf(customer.getId())));
    }
}
