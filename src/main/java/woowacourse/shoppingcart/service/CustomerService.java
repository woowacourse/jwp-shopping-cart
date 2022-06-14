package woowacourse.shoppingcart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.exception.AuthException;
import woowacourse.exception.JoinException;
import woowacourse.exception.dto.ErrorResponse;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.domain.customer.Password;
import woowacourse.shoppingcart.domain.customer.Username;

@Service
@Transactional
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Customer register(String email, String password, String username) {
        if (customerDao.existsByEmail(email)) {
            throw new JoinException("이미 존재하는 이메일입니다.", ErrorResponse.DUPLICATED_EMAIL);
        }
        final Long newId = customerDao.save(new Customer(Email.of(email), Password.ofWithEncryption(password), Username.of(username)));
        return new Customer(newId, Email.of(email), Password.ofWithEncryption(password), Username.of(username));
    }

    public void changePassword(Customer customer, String oldPassword, String newPassword) {
        if (customer.isDifferentPassword(Password.ofWithEncryption(oldPassword))) {
            throw new AuthException("기존 비밀번호와 맞지 않습니다.", ErrorResponse.INCORRECT_PASSWORD);
        }
        final Password encryptedPassword = Password.ofWithEncryption(newPassword);
        customerDao.updatePassword(customer.getId(), encryptedPassword.getValue());
    }

    public Customer changeGeneralInfo(Customer customer, String username) {
        customerDao.updateGeneralInfo(customer.getId(), username);
        return customerDao.findByEmail(customer.getEmail().getValue());
    }

    public void delete(Customer customer, String password) {
        if (customer.isDifferentPassword(Password.ofWithEncryption(password))) {
            throw new AuthException("기존 비밀번호와 맞지 않습니다.", ErrorResponse.INCORRECT_PASSWORD);
        }
        customerDao.delete(customer.getId());
    }
}
