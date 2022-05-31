package woowacourse.auth.application;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.badrequest.InvalidLoginException;
import woowacourse.shoppingcart.exception.notfound.NotFoundCustomerException;

@Service
@Transactional(rollbackFor = Exception.class)
public class AuthService {

    private final CustomerService customerService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(final CustomerService customerService, final JwtTokenProvider jwtTokenProvider) {
        this.customerService = customerService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional(readOnly = true)
    public String login(final LoginRequest request) {
        try {
            final Customer customer = customerService.getByEmail(request.getEmail());
            checkPassword(request, customer);
            return jwtTokenProvider.createToken(request.getEmail());
        } catch (final NotFoundCustomerException exception) {
            throw new InvalidLoginException();
        }
    }

    private void checkPassword(final LoginRequest request, final Customer customer) {
        if (!BCrypt.checkpw(request.getPassword(), customer.getPassword())) {
            throw new InvalidLoginException();
        }
    }
}
