package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
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
        return SignUpResponse.fromCustomer(customer);
    }

    private void validateDuplicateUsername(String username) {
        if (customerDao.existByUsername(username)) {
            throw new DuplicateUsernameException();
        }
    }

    private void validateDuplicateEmail(String email) {
        if (customerDao.existByEmail(email)) {
            throw new DuplicateEmailException();
        }
    }

    public CustomerResponse findCustomer(String username) {
        Customer customer = customerDao.findByUsername(username);
        return new CustomerResponse(customer.getUsername(), customer.getEmail());
    }

    @Transactional
    public void updateCustomer(String username, UpdatePasswordRequest updatePasswordRequest) {
        Customer customer = customerDao.findByUsername(username);
        customer.validatePassword(updatePasswordRequest.getPassword());
        customerDao.updatePassword(customer.getId(), updatePasswordRequest.getNewPassword());
    }

    @Transactional
    public void deleteCustomer(String username, DeleteCustomerRequest deleteCustomerRequest) {
        Customer customer = customerDao.findByUsername(username);
        customer.validatePassword(deleteCustomerRequest.getPassword());
        customerDao.deleteByUsername(username);
    }
}
