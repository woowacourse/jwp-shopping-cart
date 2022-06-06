package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.exception.auth.LoginFailureException;
import woowacourse.shoppingcart.application.PasswordEncoderAdapter;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.CustomerNotFoundException;

@Service
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
                .orElseThrow(CustomerNotFoundException::new);
        validatePassword(tokenRequest, customer);
        final String accessToken = jwtTokenProvider.createToken(customer.getId());
        return new TokenResponse(accessToken);
    }

    private void validatePassword(TokenRequest tokenRequest, Customer customer) {
        if (!customer.validatePassword(tokenRequest.getPassword(), new PasswordEncoderAdapter())) {
            throw new LoginFailureException();
        }
    }
}
