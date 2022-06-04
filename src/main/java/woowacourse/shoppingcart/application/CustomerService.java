package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Email;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.domain.Username;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.DeleteCustomerRequest;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.dto.SignUpResponse;
import woowacourse.shoppingcart.dto.UpdatePasswordRequest;
import woowacourse.shoppingcart.exception.DuplicateEmailException;
import woowacourse.shoppingcart.exception.DuplicateUsernameException;

@Service
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Transactional
    public SignUpResponse addCustomer(SignUpRequest signUpRequest) {
        validateDuplicateUsername(signUpRequest.getUsername());
        validateDuplicateEmail(signUpRequest.getEmail());

        Customer customer = customerDao.save(signUpRequest.toCustomer());
        return SignUpResponse.from(customer);
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

    public CustomerResponse findCustomer(String username) {
        Customer customer = customerDao.findByUsername(new Username(username));
        return CustomerResponse.from(customer);
    }

    @Transactional
    public void updateCustomer(String username, UpdatePasswordRequest updatePasswordRequest) {
        Customer customer = customerDao.findByUsername(new Username(username));
        Password password = new Password(updatePasswordRequest.getPassword());
        customer.validatePassword(password);
        customerDao.updatePassword(customer.getId(), new Password(updatePasswordRequest.getNewPassword()));
    }

    @Transactional
    public void deleteCustomer(String username, DeleteCustomerRequest deleteCustomerRequest) {
        Customer customer = customerDao.findByUsername(new Username(username));
        Password password = new Password(deleteCustomerRequest.getPassword());
        customer.validatePassword(password);
        customerDao.deleteByUsername(new Username(username));
    }
}
