package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private final CustomerDao customerDao;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(final CustomerDao customerDao, final JwtTokenProvider jwtTokenProvider) {
        this.customerDao = customerDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenResponse login(final TokenRequest request) {
        Customer customer = customerDao.findByUsername(request.getUsername());
        customer.matchPassword(request.getPassword());
        String accessToken = jwtTokenProvider.createToken(customer.getUsername());
        return new TokenResponse(accessToken);
    }
}
