package woowacourse.shoppingcart.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import woowacourse.exception.auth.PasswordIncorrectException;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.CustomerNotFoundException;
import woowacourse.shoppingcart.ui.dto.request.CustomerDeleteRequest;
import woowacourse.shoppingcart.ui.dto.request.CustomerRequest;
import woowacourse.shoppingcart.ui.dto.request.CustomerResponse;
import woowacourse.shoppingcart.ui.dto.request.CustomerUpdateRequest;

@Service
public class CustomerService {
    private final CustomerDao customerDao;

    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerDao customerDao, PasswordEncoder passwordEncoder) {
        this.customerDao = customerDao;
        this.passwordEncoder = passwordEncoder;
    }

    public void create(CustomerRequest customerRequest) {
        final String hashpw = passwordEncoder.encode(customerRequest.getPassword());
        final Customer customer = new Customer(customerRequest.getEmail(), customerRequest.getName(), hashpw);
        customerDao.save(customer);
    }

    public CustomerResponse findById(Long id) {
        final Customer customer = customerDao.findById(id).orElseThrow(CustomerNotFoundException::new);
        return new CustomerResponse(customer.getName(), customer.getEmail());
    }

    public Customer getIdByEmail(String email) {
        return customerDao.findByEmail(email).orElseThrow(CustomerNotFoundException::new);
    }

    public void update(Long id, CustomerUpdateRequest customerUpdateRequest) {
        final Customer customer = customerDao.findById(id).orElseThrow(CustomerNotFoundException::new);
        validatePassword(customer, customerUpdateRequest.getPassword());

        customerDao.update(customer.changeName(customerUpdateRequest.getName()));
    }

    private void validatePassword(Customer customer, String inputPassword) {
        if (!customer.validatePassword(inputPassword, passwordEncoder)) {
            throw new PasswordIncorrectException();
        }
    }

    public void delete(long id, CustomerDeleteRequest customerDeleteRequest) {
        final Customer customer = customerDao.findById(id).orElseThrow(CustomerNotFoundException::new);
        validatePassword(customer, customerDeleteRequest.getPassword());

        customerDao.delete(id);
    }
}
