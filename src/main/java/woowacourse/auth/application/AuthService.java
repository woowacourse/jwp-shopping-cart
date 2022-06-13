package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.PasswordRequest;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.exception.InvalidAuthException;
import woowacourse.auth.support.Encryption;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;

@Service
@Transactional(rollbackFor = Exception.class)
public class AuthService {

    private final CustomerDao customerDao;
    private final JwtTokenProvider jwtTokenProvider;
    private final Encryption encryption;

    public AuthService(final CustomerDao customerDao, final JwtTokenProvider jwtTokenProvider,
                       final Encryption encryption) {
        this.customerDao = customerDao;
        this.jwtTokenProvider = jwtTokenProvider;
        this.encryption = encryption;
    }

    public TokenResponse login(final TokenRequest request) {
        Customer customer = customerDao.findByUsername(request.getUsername());
        validatePasswordIsCorrect(customer, request.getPassword());
        String accessToken = jwtTokenProvider.createToken(Long.toString(customer.getId()));
        return new TokenResponse(accessToken);
    }

    public void checkPassword(final Long customerId, final PasswordRequest request) {
        Customer customer = customerDao.findById(customerId);
        validatePasswordIsCorrect(customer, request.getPassword());
    }

    private void validatePasswordIsCorrect(final Customer customer, final String password) {
        if (!encryption.isSame(customer.getPassword(), password)) {
            throw new InvalidAuthException("비밀번호가 일치하지 않습니다.");
        }
    }

}
