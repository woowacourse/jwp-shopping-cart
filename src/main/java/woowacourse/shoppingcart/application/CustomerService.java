package woowacourse.shoppingcart.application;

import java.util.function.Supplier;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.SignupRequest;
import woowacourse.shoppingcart.dto.UpdateCustomerRequest;
import woowacourse.shoppingcart.exception.DuplicatedUsernameException;
import woowacourse.shoppingcart.exception.EmptyResultException;
import woowacourse.shoppingcart.support.Encryptor;

@Service
@Transactional
public class CustomerService {
    private static final int PASSWORD_MIN_LENGTH = 8;
    private static final int PASSWORD_MAX_LENGTH = 20;
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z])[A-Za-z0-9]{8,20}$");

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Customer save(SignupRequest signupRequest) {
        Customer customer = Customer.of(
            signupRequest.getUsername(),
            convertPassword(signupRequest.getPassword()),
            signupRequest.getPhoneNumber(),
            signupRequest.getAddress()
        );

        if (customerDao.findByUsername(signupRequest.getUsername()).isPresent()) {
            throw new DuplicatedUsernameException();
        }

        return customerDao.save(customer);
    }

    public String convertPassword(String password) {
        validatePassword(password);
        return Encryptor.encrypt(password);
    }

    private void validatePassword(String password) {
        validateLength(password);
        validatePattern(password);
    }

    private void validateLength(String value) {
        if (value.length() < PASSWORD_MIN_LENGTH || value.length() > PASSWORD_MAX_LENGTH) {
            throw new IllegalArgumentException("패스워드의 길이는 " + PASSWORD_MIN_LENGTH + "자 이상 " + PASSWORD_MAX_LENGTH + "자 이하여야 합니다.");
        }
    }

    private void validatePattern(String value) {
        if (!PASSWORD_PATTERN.matcher(value).find()) {
            throw new IllegalArgumentException("password는 영어와 숫자로 이루어져야 합니다.");
        }
    }

    @Transactional(readOnly = true)
    public Customer findByUsername(String username) {
        return customerDao.findByUsername(username)
            .orElseThrow(throwEmptyCustomerException());
    }

    private Supplier<EmptyResultException> throwEmptyCustomerException() {
        return () -> new EmptyResultException("해당 username으로 customer를 찾을 수 없습니다.");
    }

    public void updateInfo(String username, UpdateCustomerRequest updateCustomerRequest) {
        Customer customer = findByUsername(username);
        customer.updatePhoneNumber(updateCustomerRequest.getPhoneNumber());
        customer.updateAddress(updateCustomerRequest.getAddress());
        customerDao.update(customer);
    }

    public void updatePassword(String username, UpdateCustomerRequest updateCustomerRequest) {
        Customer customer = findByUsername(username);
        customer.updatePassword(updateCustomerRequest.getPassword());
        customerDao.update(customer);
    }

    public void deleteByUsername(String username) {
        customerDao.deleteByUsername(username);
    }
}
