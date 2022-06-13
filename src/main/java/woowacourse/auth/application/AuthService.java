package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.exception.NoSuchEmailException;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Email;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private final CustomerDao customerDao;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(final CustomerDao customerDao, final JwtTokenProvider jwtTokenProvider) {
        this.customerDao = customerDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String certify(final TokenRequest tokenRequest) {
        final Customer customer = customerDao.findByEmail(new Email(tokenRequest.getEmail()))
                .orElseThrow(NoSuchEmailException::new);
        customer.checkPasswordMatch(tokenRequest.getPassword());
        return jwtTokenProvider.createToken(String.valueOf(customer.getId()));
    }
}
