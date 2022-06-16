package woowacourse.shoppingcart.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.auth.dto.LoginRequest;
import woowacourse.shoppingcart.auth.exception.badrequest.InvalidLoginException;
import woowacourse.shoppingcart.customer.application.CustomerService;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.customer.exception.notfound.NotFoundCustomerException;
import woowacourse.shoppingcart.support.JwtTokenProvider;

@Service
@Transactional
public class AuthService {

    private final CustomerService customerService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(final CustomerService customerService, final JwtTokenProvider jwtTokenProvider) {
        this.customerService = customerService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional(readOnly = true)
    public String login(final LoginRequest request) {
        final Customer customer = fetchCustomer(request.getEmail());
        checkPassword(request, customer);
        return jwtTokenProvider.createToken(request.getEmail());
    }

    private Customer fetchCustomer(final String email) {
        try {
            return customerService.getByEmail(email);
        } catch (final NotFoundCustomerException exception) {
            throw new InvalidLoginException();
        }
    }

    private void checkPassword(final LoginRequest request, final Customer customer) {
        final String plainPassword = request.getPassword();
        if (!customer.isSamePassword(plainPassword)) {
            throw new InvalidLoginException();
        }
    }
}
