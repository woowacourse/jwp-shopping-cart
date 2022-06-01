package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.PasswordEncoder;
import woowacourse.shoppingcart.domain.customer.PlainPassword;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private final CustomerDao customerDao;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthService(final CustomerDao customerDao, final JwtTokenProvider jwtTokenProvider,
                       final PasswordEncoder passwordEncoder) {
        this.customerDao = customerDao;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public TokenResponse login(final TokenRequest request) {
        Customer customer = customerDao.findByUsername(request.getUsername());
        PlainPassword requestPassword = new PlainPassword(request.getPassword());
        customer.matchPassword(passwordEncoder, requestPassword);

        String accessToken = jwtTokenProvider.createToken(customer.getUsername());
        return new TokenResponse(accessToken);
    }
}
