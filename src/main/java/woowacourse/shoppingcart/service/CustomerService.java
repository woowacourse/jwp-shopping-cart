package woowacourse.shoppingcart.service;

import org.springframework.stereotype.Service;
import woowacourse.exception.AuthException;
import woowacourse.exception.JoinException;
import woowacourse.exception.dto.ErrorResponse;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.dto.CustomerResponse;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Customer register(String email, String password, String username) {
        if (customerDao.existsByEmail(email)) {
            throw new JoinException("이미 존재하는 이메일입니다.", ErrorResponse.DUPLICATED_EMAIL);
        }
        final Long newId = customerDao.save(new Customer(email, Password.ofWithEncryption(password), username));

        return new Customer(newId, email, Password.ofWithEncryption(password), username);
    }

    public void changePassword(Customer customer, String oldPassword, String newPassword) {
        if (customer.isDifferentPassword(Password.ofWithEncryption(oldPassword))) {
            throw new AuthException("기존 비밀번호와 맞지 않습니다.", ErrorResponse.INCORRECT_PASSWORD);
        }
        final Password encryptedPassword = Password.ofWithEncryption(newPassword);
        customerDao.updatePassword(customer.getId(), encryptedPassword.getValue());
    }

    public CustomerResponse changeGeneralInfo(Customer customer, String username) {
        customerDao.updateGeneralInfo(customer.getId(), username);
        final Customer updatedCustomer = customerDao.findByEmail(customer.getEmail());
        return new CustomerResponse(updatedCustomer.getEmail(), updatedCustomer.getUsername());
    }

    public void delete(Customer customer, String password) {
        if (customer.isDifferentPassword(Password.ofWithEncryption(password))) {
            throw new AuthException("기존 비밀번호와 맞지 않습니다.", ErrorResponse.INCORRECT_PASSWORD);
        }
        customerDao.delete(customer.getId());
    }
}
