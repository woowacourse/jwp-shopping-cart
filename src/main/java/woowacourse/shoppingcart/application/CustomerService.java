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
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.InvalidPasswordException;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Transactional
    public SignUpResponse addCustomer(SignUpRequest signUpRequest) {
        Customer customer = customerDao.save(signUpRequest.toCustomer());
        return SignUpResponse.fromCustomer(customer);
    }

    @Transactional(readOnly = true)
    public CustomerResponse findMe(String username) {
        Customer customer = customerDao.findByUsername(username);
        return new CustomerResponse(customer.getUsername(), customer.getEmail());
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
        if (!customerDao.existByUsername(username)) {
            throw new InvalidCustomerException();
        }
        if (!customerDao.isValidPasswordByUsername(username, deleteCustomerRequest.getPassword())) {
            throw new InvalidPasswordException();
        }
        customerDao.deleteByUsername(username);
    }
}
