package woowacourse.customer.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.auth.exception.InvalidLoginException;
import woowacourse.customer.dao.CustomerDao;
import woowacourse.customer.domain.Customer;
import woowacourse.customer.domain.PlainPassword;
import woowacourse.customer.dto.SignupRequest;
import woowacourse.customer.dto.UpdateCustomerRequest;
import woowacourse.customer.dto.UpdatePasswordRequest;
import woowacourse.customer.exception.InvalidCustomerException;
import woowacourse.customer.support.passwordencoder.PasswordEncoder;

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

        final PlainPassword plainPassword = new PlainPassword(signupRequest.getPassword());
        final Customer customer = signupRequest.toCustomer(passwordEncoder.encode(plainPassword.getValue()));

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
            .orElseThrow(() -> new InvalidLoginException("해당하는 사용자 이름이 없습니다."));
    }

    @Transactional(readOnly = true)
    public Long findIdByUsername(final String username) {
        return customerDao.findIdByUserName(username);
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

    public void updatePassword(final String username, final UpdatePasswordRequest updatePasswordRequest) {
        final Customer customer = findByUsername(username);
        final PlainPassword plainPassword = new PlainPassword(updatePasswordRequest.getPassword());
        customer.updatePassword(passwordEncoder.encode(plainPassword.getValue()));
        customerDao.update(customer);
    }

    public void deleteByUsername(final String username) {
        customerDao.deleteByUsername(username);
    }
}
