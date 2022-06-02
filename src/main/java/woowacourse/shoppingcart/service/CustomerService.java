package woowacourse.shoppingcart.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.exception.CustomerNotFoundException;
import woowacourse.exception.EmailDuplicateException;
import woowacourse.exception.PasswordIncorrectException;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.ui.dto.request.CustomerDeleteRequest;
import woowacourse.shoppingcart.ui.dto.request.CustomerRequest;
import woowacourse.shoppingcart.ui.dto.request.CustomerResponse;
import woowacourse.shoppingcart.ui.dto.request.CustomerUpdatePasswordRequest;
import woowacourse.shoppingcart.ui.dto.request.CustomerUpdateProfileRequest;

@Transactional(readOnly = true)
@Service
public class CustomerService {
    private final CustomerDao customerDao;

    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerDao customerDao, PasswordEncoder passwordEncoder) {
        this.customerDao = customerDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public long create(CustomerRequest customerRequest) {
        validateDuplicateEmail(customerRequest);

        final String hashpw = passwordEncoder.encode(customerRequest.getPassword());
        final Customer customer = new Customer(customerRequest.getEmail(), customerRequest.getName(), hashpw);

        return customerDao.save(customer);
    }

    private void validateDuplicateEmail(CustomerRequest customerRequest) {
        if (customerDao.findByEmail(customerRequest.getEmail()).isPresent()) {
            throw new EmailDuplicateException();
        }
    }

    public CustomerResponse findById(Long id) {
        final Customer customer = customerDao.findById(id).orElseThrow(CustomerNotFoundException::new);
        return new CustomerResponse(customer.getName(), customer.getEmail());
    }

    public Customer getByEmail(String email) {
        return customerDao.findByEmail(email).orElseThrow(CustomerNotFoundException::new);
    }

    @Transactional
    public long updateProfile(Long id, CustomerUpdateProfileRequest customerUpdateProfileRequest) {
        final Customer customer = customerDao.findById(id).orElseThrow(CustomerNotFoundException::new);

        customerDao.updateProfile(customer.changeName(customerUpdateProfileRequest.getName()));
        return id;
    }

    @Transactional
    public long updatePassword(Long id, CustomerUpdatePasswordRequest customerUpdatePasswordRequest) {
        final Customer customer = customerDao.findById(id).orElseThrow(CustomerNotFoundException::new);
        validatePassword(customer, customerUpdatePasswordRequest.getOldPassword());

        customerDao.updatePassword(
                customer.changePassword(passwordEncoder.encode(customerUpdatePasswordRequest.getNewPassword())));
        return id;
    }

    private void validatePassword(Customer customer, String inputPassword) {
        if (!customer.validatePassword(inputPassword, passwordEncoder)) {
            throw new PasswordIncorrectException();
        }
    }

    @Transactional
    public long delete(long id, CustomerDeleteRequest customerDeleteRequest) {
        final Customer customer = customerDao.findById(id).orElseThrow(CustomerNotFoundException::new);
        validatePassword(customer, customerDeleteRequest.getPassword());

        customerDao.delete(id);
        return id;
    }
}
