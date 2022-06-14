package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.common.exception.AuthException;
import woowacourse.common.exception.JoinException;
import woowacourse.common.exception.dto.ErrorResponse;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Password;
import woowacourse.shoppingcart.dto.customer.CustomerResponse;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void register(String email, String password, String username) {
        if (customerDao.existsByEmail(email)) {
            throw new JoinException("이미 존재하는 이메일입니다.", ErrorResponse.DUPLICATED_EMAIL);
        }
        customerDao.save(new Customer(email, Password.fromPlainInput(password), username));
    }

    public CustomerResponse showCustomer(String email) {
        final Customer customer = customerDao.getByEmail(email);

        return new CustomerResponse(customer.getEmail(), customer.getUsername());
    }

    public void changePassword(String email, String oldPassword, String newPassword) {
        final Customer customer = customerDao.getByEmail(email);
        checkPassword(customer, oldPassword);
        final Password encryptedPassword = Password.fromPlainInput(newPassword);
        customerDao.updatePassword(customer.getId(), encryptedPassword.getValue());
    }

    public CustomerResponse changeGeneralInfo(String email, String username) {
        final Customer customer = customerDao.getByEmail(email);
        customerDao.updateGeneralInfo(customer.getId(), username);
        final Customer updatedCustomer = customerDao.getByEmail(email);
        return new CustomerResponse(updatedCustomer.getEmail(), updatedCustomer.getUsername());
    }

    public void delete(String email, String password) {
        final Customer customer = customerDao.getByEmail(email);
        checkPassword(customer, password);
        customerDao.delete(customer.getId());
    }

    private void checkPassword(Customer customer, String password) {
        if (customer.isDifferentPassword(password)) {
            throw new AuthException("기존 비밀번호와 맞지 않습니다.", ErrorResponse.INCORRECT_PASSWORD);
        }
    }
}
