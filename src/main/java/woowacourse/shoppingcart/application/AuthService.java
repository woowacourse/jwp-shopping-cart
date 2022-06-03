package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.LoginRequest;
import woowacourse.shoppingcart.exception.badrequest.InvalidLoginException;
import woowacourse.shoppingcart.exception.notfound.NotFoundCustomerException;
import woowacourse.shoppingcart.support.JwtTokenProvider;

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
