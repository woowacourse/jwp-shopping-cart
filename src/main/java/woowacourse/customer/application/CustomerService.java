package woowacourse.customer.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.auth.exception.InvalidLoginException;
import woowacourse.customer.dao.CustomerDao;
import woowacourse.customer.domain.Customer;
import woowacourse.customer.domain.PlainPassword;
import woowacourse.customer.dto.CustomerResponse;
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

    public void save(final SignupRequest signupRequest) {
        validateDuplicateUsername(signupRequest.getUsername());

        final PlainPassword plainPassword = new PlainPassword(signupRequest.getPassword());
        final Customer customer = signupRequest.toCustomer(passwordEncoder.encode(plainPassword.getValue()));

        customerDao.save(customer);
    }

    private void validateDuplicateUsername(final String username) {
        if (customerDao.existsByUsername(username)) {
            throw new InvalidCustomerException("이미 존재하는 사용자 이름입니다.");
        }
    }

    @Transactional(readOnly = true)
    public CustomerResponse findCustomerByUsername(final String username) {
        final Customer customer = customerDao.findByUsername(username)
            .orElseThrow(() -> new InvalidLoginException("해당하는 사용자 이름이 없습니다."));

        return CustomerResponse.from(customer);
    }

    @Transactional(readOnly = true)
    public Long findCustomerIdByUsername(final String username) {
        return customerDao.findIdByUsername(username);
    }

    public void updateInfo(final String username, final UpdateCustomerRequest updateCustomerRequest) {
        final Customer customer = customerDao.findByUsername(username)
            .orElseThrow(() -> new InvalidLoginException("해당하는 사용자 이름이 없습니다."));
        customer.updatePhoneNumber(updateCustomerRequest.getPhoneNumber());
        customer.updateAddress(updateCustomerRequest.getAddress());
        customerDao.update(customer);
    }

    public void confirmPassword(final String username, final String password) {
        final Customer customer = customerDao.findByUsername(username)
            .orElseThrow(() -> new InvalidLoginException("해당하는 사용자 이름이 없습니다."));
        customer.matchesPassword(passwordEncoder, password);
    }

    public void updatePassword(final String username, final UpdatePasswordRequest updatePasswordRequest) {
        final Customer customer = customerDao.findByUsername(username)
            .orElseThrow(() -> new InvalidLoginException("해당하는 사용자 이름이 없습니다."));
        final PlainPassword plainPassword = new PlainPassword(updatePasswordRequest.getPassword());
        customer.updatePassword(passwordEncoder.encode(plainPassword.getValue()));
        customerDao.update(customer);
    }

    public void deleteCustomerByUsername(final String username) {
        customerDao.deleteByUsername(username);
    }
}
