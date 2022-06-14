package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.DeleteCustomerRequest;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.dto.SignUpResponse;
import woowacourse.shoppingcart.dto.UpdatePasswordRequest;
import woowacourse.shoppingcart.exception.*;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Transactional
    public SignUpResponse addCustomer(SignUpRequest signUpRequest) {
        validateDuplicateUsername(signUpRequest);
        validateDuplicateEmail(signUpRequest);
        Customer customer = customerDao.save(signUpRequest.toCustomer());
        return SignUpResponse.from(customer);
    }

    private void validateDuplicateUsername(SignUpRequest signUpRequest) {
        if (customerDao.existByUserName(signUpRequest.getUsername())) {
            throw new DuplicateUsernameException();
        }
    }

    private void validateDuplicateEmail(SignUpRequest signUpRequest) {
        if (customerDao.existByEmail(signUpRequest.getEmail())) {
            throw new DuplicateEmailException();
        }
    }

    @Transactional(readOnly = true)
    public CustomerResponse findMe(String username) {
        Customer customer = customerDao.findByUsername(username);
        return CustomerResponse.from(customer);
    }

    @Transactional
    public void updateMe(String username, UpdatePasswordRequest updatePasswordRequest) {
        Customer customer = customerDao.findByUsername(username);
        if (!customerDao.isValidPasswordByUsername(username, updatePasswordRequest.getPassword())) {
            throw new InvalidPasswordException();
        }

        customerDao.updatePassword(customer.getId(), updatePasswordRequest.getNewPassword());
    }

    @Transactional
    public void deleteMe(String username, DeleteCustomerRequest deleteCustomerRequest) {
        if (!customerDao.existByUserName(username)) {
            throw new InvalidCustomerException();
        }
        if (!customerDao.isValidPasswordByUsername(username, deleteCustomerRequest.getPassword())) {
            throw new InvalidPasswordException();
        }
        customerDao.deleteByUserName(username);
    }
}
