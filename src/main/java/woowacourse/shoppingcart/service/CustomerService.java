package woowacourse.shoppingcart.service;

import org.springframework.stereotype.Service;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.ChangePasswordRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.DeleteCustomerRequest;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.dto.SignUpResponse;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        String name = signUpRequest.getUsername();
        String email = signUpRequest.getEmail();
        String password = signUpRequest.getPassword();

        if (customerDao.isValidName(name)) {
            throw new IllegalArgumentException("[ERROR] 이미 존재하는 사용자 이름입니다.");
        }

        if (customerDao.isValidEmail(email)) {
            throw new IllegalArgumentException("[ERROR] 이미 존재하는 이메일입니다.");
        }

        customerDao.saveCustomer(name, email, password);

        return new SignUpResponse(name, email);
    }

    public CustomerResponse findCustomerInformation(String username) {
        Customer customer = customerDao.findCustomerByUserName(username);
        String email = customer.getEmail();
        return new CustomerResponse(username, email);
    }

    public void changePassword(String username, ChangePasswordRequest changePasswordRequest) {
        Customer customer = customerDao.findCustomerByUserName(username);
        if (!customer.isSamePassword(changePasswordRequest.getOldPassword())) {
            throw new IllegalArgumentException("[ERROR] 비밀번호가 일치하지 않습니다.");
        }
        customerDao.updatePassword(username, changePasswordRequest.getNewPassword());
    }

    public void deleteUser(String username, DeleteCustomerRequest deleteCustomerRequest) {
        var customer = customerDao.findCustomerByUserName(username);
        if (!customer.isSamePassword(deleteCustomerRequest.getPassword())) {
            throw new IllegalArgumentException("[ERROR] 비밀번호가 일치하지 않습니다.");
        }
        customerDao.deleteByName(username);
    }
}
