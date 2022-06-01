package woowacourse.shoppingcart.service;

import org.springframework.stereotype.Service;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.ChangePasswordRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.DeleteCustomerRequest;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.dto.SignUpResponse;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
public class CustomerService {

    private static final String DUPLICATED_NAME = "[ERROR] 이미 존재하는 사용자 이름입니다.";
    private static final String DUPLICATED_EMAIL = "[ERROR] 이미 존재하는 이메일입니다.";
    private static final String NOT_MATCH_PASSWORD = "[ERROR] 비밀번호가 일치하지 않습니다.";
    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        String name = signUpRequest.getUsername();
        String email = signUpRequest.getEmail();
        String password = signUpRequest.getPassword();

        validateDuplicatedName(name);

        validatedDuplicatedEmail(email);

        customerDao.saveCustomer(name, email, password);

        return new SignUpResponse(name, email);
    }

    private void validatedDuplicatedEmail(String email) {
        if (customerDao.isValidEmail(email)) {
            throw new InvalidCustomerException(DUPLICATED_EMAIL);
        }
    }

    private void validateDuplicatedName(String name) {
        if (customerDao.isValidName(name)) {
            throw new InvalidCustomerException(DUPLICATED_NAME);
        }
    }

    public CustomerResponse findCustomerInformation(String username) {
        Customer customer = customerDao.findCustomerByUserName(username);
        String email = customer.getEmail();
        return new CustomerResponse(username, email);
    }

    public void changePassword(String username, ChangePasswordRequest changePasswordRequest) {
        Customer customer = customerDao.findCustomerByUserName(username);
        String password = changePasswordRequest.getOldPassword();
        validateSamePassword(password, customer);
        customerDao.updatePassword(username, changePasswordRequest.getNewPassword());
    }

    private void validateSamePassword(String password, Customer customer) {
        if (!customer.isSamePassword(password)) {
            throw new InvalidCustomerException(NOT_MATCH_PASSWORD);
        }
    }

    public void deleteUser(String username, DeleteCustomerRequest deleteCustomerRequest) {
        var customer = customerDao.findCustomerByUserName(username);
        String password = deleteCustomerRequest.getPassword();
        validateSamePassword(password, customer);
        customerDao.deleteByName(username);
    }
}
