package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Password;
import woowacourse.shoppingcart.dto.SignupRequest;
import woowacourse.shoppingcart.dto.UpdateCustomerRequest;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.UserNotFoundException;
import woowacourse.shoppingcart.support.passwordencoder.PasswordEncoder;

@Transactional
@Service
public class CustomerService {

    private final CustomerDao customerDao;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(
        final CustomerDao customerDao,
        final PasswordEncoder passwordEncoder
    ) {
        this.customerDao = customerDao;
        this.passwordEncoder = passwordEncoder;
    }

    public Customer save(final SignupRequest signupRequest) {
        validateDuplicateUsername(signupRequest.getUsername());

        final Password rawPassword = new Password(signupRequest.getPassword());
        final Customer customer = signupRequest.toCustomer(passwordEncoder.encode(rawPassword.getValue()));

        return customerDao.save(customer);
    }

    private void validateDuplicateUsername(final String username) {
        if (customerDao.existsByUsername(username)) {
            throw new InvalidCustomerException("이미 존재하는 username입니다.");
        }
    }

    @Transactional(readOnly = true)
    public Customer findByUsername(final String username) {
        return customerDao.findByUsername(username)
            .orElseThrow(UserNotFoundException::new);
    }

    public void updateInfo(final String username, final UpdateCustomerRequest updateCustomerRequest) {
        final Customer customer = findByUsername(username);
        customer.updatePhoneNumber(updateCustomerRequest.getPhoneNumber());
        customer.updateAddress(updateCustomerRequest.getAddress());
        customerDao.update(customer);
    }

    public void confirmPassword(final String username, final String password) {
        final Customer customer = findByUsername(username);
        customer.getPassword().matches(passwordEncoder, password);
    }

    public void updatePassword(final String username, final UpdateCustomerRequest updateCustomerRequest) {
        final Customer customer = findByUsername(username);
        final Password password = new Password(updateCustomerRequest.getPassword());
        customer.updatePassword(passwordEncoder.encode(password.getValue()));
        customerDao.update(customer);
    }

    public void deleteByUsername(final String username) {
        customerDao.deleteByUsername(username);
    }
}
