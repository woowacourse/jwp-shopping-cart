package woowacourse.auth.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final PasswordEncoder passwordEncoder;

    public AuthService(final CustomerDao customerDao,
                       final JwtTokenProvider jwtTokenProvider,
                       final PasswordEncoder passwordEncoder) {
        this.customerDao = customerDao;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public TokenResponse createToken(final TokenRequest tokenRequest) {
        final Customer customer = customerDao.findByAccount(tokenRequest.getAccount())
                .orElseThrow(LoginFailException::new);

        if (customer.checkPasswordNotMatch(passwordEncoder, tokenRequest.getPassword())) {
            throw new LoginFailException();
        }

        return new TokenResponse(jwtTokenProvider.createToken(String.valueOf(customer.getId())));
    }
}
