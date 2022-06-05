package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.application.dto.LoginServiceRequest;
import woowacourse.auth.exception.NoSuchEmailException;
import woowacourse.auth.exception.PasswordNotMatchException;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Email;

@Service
public class AuthService {

    private final CustomerDao customerDao;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(final CustomerDao customerDao, final JwtTokenProvider jwtTokenProvider) {
        this.customerDao = customerDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String certify(final LoginServiceRequest loginServiceRequest) {
        final Customer customer = customerDao.findByEmail(new Email(loginServiceRequest.getEmail()))
                .orElseThrow(NoSuchEmailException::new);
        if (customer.unMatchPasswordWith(loginServiceRequest.getPassword())) {
            throw new PasswordNotMatchException();
        }

        return jwtTokenProvider.createToken(String.valueOf(customer.getId()));
    }

    public Long parseToId(final String token) {
        return Long.parseLong(jwtTokenProvider.getPayload(token));
    }

    public boolean isValidToken(final String token) {
        return jwtTokenProvider.validateToken(token);
    }
}
