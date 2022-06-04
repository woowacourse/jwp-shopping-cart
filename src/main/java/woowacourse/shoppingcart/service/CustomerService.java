package woowacourse.shoppingcart.service;

import org.springframework.stereotype.Service;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.AuthorizedCustomer;
import woowacourse.shoppingcart.dto.ChangePasswordRequest;
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
        if (customerDao.isExistEmail(email)) {
            throw new InvalidCustomerException(DUPLICATED_EMAIL);
        }
    }

    private void validateDuplicatedName(String name) {
        if (customerDao.isExistName(name)) {
            throw new InvalidCustomerException(DUPLICATED_NAME);
        }
    }

//    public CustomerResponse findCustomerInformation(String username) {
//        var customer = customerDao.findCustomerByUserName(username);
//        String email = customer.getEmail();
//        return new CustomerResponse(username, email);
//    }

    public void changePassword(AuthorizedCustomer authorizedCustomer, ChangePasswordRequest changePasswordRequest) {
        String password = changePasswordRequest.getOldPassword();
        var customer = convertCustomer(authorizedCustomer);
        validateSamePassword(password, customer);
        customerDao.updatePassword(customer.getUsername(), changePasswordRequest.getNewPassword());
    }

    private Customer convertCustomer(AuthorizedCustomer authorizedCustomer) {
        return new Customer(
                authorizedCustomer.getUsername(),
                authorizedCustomer.getEmail(),
                authorizedCustomer.getPassword()
        );
    }

    private void validateSamePassword(String password, Customer customer) {
        if (!customer.isSamePassword(password)) {
            throw new InvalidCustomerException(NOT_MATCH_PASSWORD);
        }
    }

    public void deleteUser(AuthorizedCustomer authorizedCustomer, DeleteCustomerRequest deleteCustomerRequest) {
        String password = deleteCustomerRequest.getPassword();
        var customer = convertCustomer(authorizedCustomer);
        validateSamePassword(password, customer);
        customerDao.deleteByName(customer.getUsername());
    }
}
