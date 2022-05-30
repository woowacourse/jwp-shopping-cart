package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.domain.Customer;
import woowacourse.auth.dto.CustomerResponse;
import woowacourse.auth.dto.DeleteCustomerRequest;
import woowacourse.auth.dto.SignUpRequest;
import woowacourse.auth.dto.SignUpResponse;
import woowacourse.shoppingcart.dao.CustomerDao;
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

    public CustomerResponse findMe(String email) {
        Customer customer = customerDao.findByEmail(email);
        return new CustomerResponse(customer.getUsername(), customer.getEmail());
    }

    public void updateMe(String email, UpdatePasswordRequest updatePasswordRequest) {
        Customer customer = customerDao.findByEmail(email);

        if (!customerDao.existByEmailAndPassword(email, updatePasswordRequest.getPassword())) {
            throw new InvalidPasswordException();
        }

        customerDao.updatePassword(customer.getId(), updatePasswordRequest.getNewPassword());
    }

    public void deleteMe(String username, DeleteCustomerRequest deleteCustomerRequest) {
        if (!customerDao.existByUserName(username)) {
            throw new InvalidCustomerException();
        }
        if (!customerDao.isValidPassword(username, deleteCustomerRequest.getPassword())) {
            throw new InvalidPasswordException();
        }
        customerDao.deleteByUserName(username);
    }
}
