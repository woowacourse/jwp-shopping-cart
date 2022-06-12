package woowacourse.shoppingcart.customer.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.exception.AuthException;
import woowacourse.exception.JoinException;
import woowacourse.exception.dto.ErrorResponse;
import woowacourse.shoppingcart.customer.dao.CustomerDao;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.customer.domain.Password;
import woowacourse.shoppingcart.customer.dto.CustomerResponse;

@Service
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Transactional
    public void register(String email, String password, String username) {
        final Password encryptedPassword = Password.from(password);
        if(customerDao.existsByEmail(email)){
            throw new JoinException("이미 존재하는 이메일입니다.", ErrorResponse.DUPLICATED_EMAIL);
        }
        customerDao.save(new Customer(email, encryptedPassword.getPassword(), username));
    }

    public CustomerResponse showCustomer(String email) {
        final Customer customer = customerDao.findByEmail(email);

        return new CustomerResponse(customer.getEmail(), customer.getUsername());
    }

    @Transactional
    public void changePassword(String email, String oldPassword, String newPassword) {
        final Customer customer = customerDao.findByEmail(email);
        if(customer.isDifferentPassword(oldPassword)){
            throw new AuthException("기존 비밀번호와 맞지 않습니다.", ErrorResponse.INCORRECT_PASSWORD);
        }
        final Password encryptedPassword = Password.from(newPassword);
        customerDao.updatePassword(customer.getId(), encryptedPassword.getPassword());
    }

    @Transactional
    public CustomerResponse changeGeneralInfo(String email, String username) {
        final Customer customer = customerDao.findByEmail(email);
        customerDao.updateGeneralInfo(customer.getId(), username);
        final Customer updatedCustomer = customerDao.findByEmail(email);
        return new CustomerResponse(updatedCustomer.getEmail(), updatedCustomer.getUsername());
    }

    @Transactional
    public void delete(String email, String password) {
        final Customer customer = customerDao.findByEmail(email);
        if(customer.isDifferentPassword(password)){
            throw new AuthException("기존 비밀번호와 맞지 않습니다.", ErrorResponse.INCORRECT_PASSWORD);
        }
        customerDao.delete(customer.getId());

    }
}
