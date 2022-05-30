package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.dto.CustomerResponse;

@Service
public class CustomerService {

    private final CustomerDao customerDao;
    private final JwtTokenProvider jwtTokenProvider;

    public CustomerService(CustomerDao customerDao, JwtTokenProvider jwtTokenProvider) {
        this.customerDao = customerDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void register(String email, String password, String username) {
        final Password encryptedPassword = Password.from(password);
        customerDao.save(new Customer(email, encryptedPassword.getPassword(), username));
    }

    public CustomerResponse showCustomer(String token) {
        final String email = jwtTokenProvider.getPayload(token);
        final Customer customer = customerDao.findByEmail(email);

        return new CustomerResponse(customer.getEmail(), customer.getUsername());
    }
}
