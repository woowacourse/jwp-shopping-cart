package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.application.dto.LoginServiceRequest;
import woowacourse.auth.exception.NoSuchEmailException;
import woowacourse.auth.exception.PasswordNotMatchException;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;

@Service
public class AuthService {

    private final CustomerDao customerDao;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(final CustomerDao customerDao, final JwtTokenProvider jwtTokenProvider) {
        this.customerDao = customerDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String certify(final LoginServiceRequest loginServiceRequest) {
        final Customer customer = customerDao.findByEmail(loginServiceRequest.getEmail())
                .orElseThrow(NoSuchEmailException::new);
        if (!customer.isSamePassword(loginServiceRequest.getPassword())) {
            throw new PasswordNotMatchException();
        }

        return jwtTokenProvider.createToken(String.valueOf(customer.getId()));
    }
}
