package woowacourse.shoppingcart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.*;
import woowacourse.shoppingcart.dto.ChangePasswordRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.DeleteCustomerRequest;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.dto.SignUpResponse;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
@Transactional
public class CustomerService {

    private static final String DUPLICATED_NAME = "[ERROR] 이미 존재하는 사용자 이름입니다.";
    private static final String DUPLICATED_EMAIL = "[ERROR] 이미 존재하는 이메일입니다.";

    private static final String NOT_EXIST_NAME = "[ERROR] 존재하지 않는 이름입니다.";

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
        Customer customer = new Customer(name, email, password);
        String encodedPassword = customer.generateEncodedPassword();
        customerDao.saveCustomer(name, email, encodedPassword);

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
        validateExistName(username);
        Customer customer = customerDao.findCustomerByUserName(username);
        return new CustomerResponse(username, customer.getEmail());
    }

    private void validateExistName(String username) {
        if (!customerDao.isValidName(username)) {
            throw new InvalidCustomerException(NOT_EXIST_NAME);
        }
    }

    public void changePassword(String username, ChangePasswordRequest changePasswordRequest) {
        Customer customer = customerDao.findCustomerByUserName(username);
        Customer requestCustomer = new Customer(username, customer.getEmail(), changePasswordRequest.getPassword());
        customer.validateSamePassword(requestCustomer);
        Customer responseCustomer = customer.changePassword(changePasswordRequest.getNewPassword());
        String newEncodedPassword = responseCustomer.generateEncodedPassword();
        customerDao.updatePassword(username, newEncodedPassword);
    }

    public void deleteUser(String username, DeleteCustomerRequest deleteCustomerRequest) {
        Customer customer= customerDao.findCustomerByUserName(username);
        Customer requestCustomer = new Customer(username, customer.getEmail(), deleteCustomerRequest.getPassword());
        customer.validateSamePassword(requestCustomer);
        customerDao.deleteByName(username);
    }
}
