package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.domain.Customer;
import woowacourse.shoppingcart.dao.CustomerDao;
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

    public SignUpResponse addCustomer(SignUpRequest signUpRequest) {
        Customer customer = customerDao.save(signUpRequest.toCustomer());
        return SignUpResponse.fromCustomer(customer);
    }

    public CustomerResponse findMe(String username) {
        Customer customer = customerDao.findByUsername(username);
        return new CustomerResponse(customer.getUsername(), customer.getEmail());
    }

    public void updateMe(String username, UpdatePasswordRequest updatePasswordRequest) {
        Customer customer = customerDao.findByUsername(username);
        validateCustomer(username, updatePasswordRequest.getPassword());
        customerDao.updatePassword(customer.getId(), updatePasswordRequest.getNewPassword());
    }

    public void deleteMe(String username, DeleteCustomerRequest deleteCustomerRequest) {
        validateCustomer(username, deleteCustomerRequest.getPassword());
        customerDao.deleteByUserName(username);
    }

    private void validateCustomer(String username, String updatePasswordRequest) {
        if (!customerDao.existByUserName(username)) {
            throw new InvalidCustomerException();
        }

        if (!customerDao.isValidPasswordByUsername(username, updatePasswordRequest)) {
            throw new InvalidPasswordException();
        }
    }
}
