package woowacourse.shoppingcart.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Email;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.domain.Username;
import woowacourse.shoppingcart.dto.request.DeleteCustomerRequest;
import woowacourse.shoppingcart.dto.request.SignUpRequest;
import woowacourse.shoppingcart.dto.request.UpdatePasswordRequest;
import woowacourse.shoppingcart.dto.response.CustomerResponse;
import woowacourse.shoppingcart.dto.response.SignUpResponse;
import woowacourse.shoppingcart.exception.DuplicateEmailException;
import woowacourse.shoppingcart.exception.DuplicateUsernameException;
import woowacourse.shoppingcart.exception.InvalidPasswordException;

@Service
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerDao customerDao;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerDao customerDao, PasswordEncoder passwordEncoder) {
        this.customerDao = customerDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public SignUpResponse addCustomer(SignUpRequest signUpRequest) {
        validateDuplicateUsername(signUpRequest.getUsername());
        validateDuplicateEmail(signUpRequest.getEmail());

        String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());
        Customer customer = new Customer(signUpRequest.getUsername(), signUpRequest.getEmail(), encodedPassword);
        Customer foundCustomer = customerDao.save(customer);

        return SignUpResponse.from(foundCustomer);
    }

    public CustomerResponse findCustomer(String username) {
        Customer customer = customerDao.findByUsername(new Username(username));
        return CustomerResponse.from(customer);
    }

    @Transactional
    public void updateCustomer(String username, UpdatePasswordRequest updatePasswordRequest) {
        Customer customer = customerDao.findByUsername(new Username(username));
        validatePassword(updatePasswordRequest.getPassword(), customer.getPassword().getValue());
        String newEncodePassword = passwordEncoder.encode(updatePasswordRequest.getNewPassword());
        customerDao.updatePassword(customer.getId(), new Password(newEncodePassword));
    }

    @Transactional
    public void deleteCustomer(String username, DeleteCustomerRequest deleteCustomerRequest) {
        Customer customer = customerDao.findByUsername(new Username(username));
        validatePassword(deleteCustomerRequest.getPassword(), customer.getPassword().getValue());
        customerDao.deleteByUsername(new Username(username));
    }

    private void validateDuplicateUsername(String username) {
        if (customerDao.existByUsername(new Username(username))) {
            throw new DuplicateUsernameException();
        }
    }

    private void validateDuplicateEmail(String email) {
        if (customerDao.existByEmail(new Email(email))) {
            throw new DuplicateEmailException();
        }
    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new InvalidPasswordException();
        }
    }
}
