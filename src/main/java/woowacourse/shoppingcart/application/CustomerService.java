package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.exception.AuthException;
import woowacourse.exception.dto.ErrorResponse;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.dto.CustomerResponse;

@Service
public class CustomerService {

    private final CustomerDao customerDao;
    private final JwtTokenProvider jwtTokenProvider;

    public CustomerService(CustomerDao customerDao, JwtTokenProvider jwtTokenProvider) {
        this.customerDao = customerDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void register(String email, String password, String username) {
        final Password encryptedPassword = Password.from(password);
        customerDao.save(new Customer(email, encryptedPassword.getPassword(), username));
    }

    public CustomerResponse showCustomer(String email) {
        final Customer customer = customerDao.findByEmail(email);

        return new CustomerResponse(customer.getEmail(), customer.getUsername());
    }

    public void changePassword(String email, String oldPassword, String newPassword) {
        final Customer customer = customerDao.findByEmail(email);
        if(customer.isDifferentPassword(oldPassword)){
            throw new AuthException("기존 비밀번호와 맞지 않습니다.", ErrorResponse.INCORRECT_PASSWORD);
        }
        final Password encryptedPassword = Password.from(newPassword);
        customerDao.updatePassword(customer.getId(), encryptedPassword.getPassword());
    }

    public CustomerResponse changeGeneralInfo(String email, String username) {
        final Customer customer = customerDao.findByEmail(email);
        customerDao.updateGeneralInfo(customer.getId(), username);
        final Customer updatedCustomer = customerDao.findByEmail(email);
        return new CustomerResponse(updatedCustomer.getEmail(), updatedCustomer.getUsername());
    }

    public void delete(String email, String password) {
        final Customer customer = customerDao.findByEmail(email);
        if(customer.isDifferentPassword(password)){
            throw new AuthException("기존 비밀번호와 맞지 않습니다.", ErrorResponse.INCORRECT_PASSWORD);
        }
        customerDao.delete(customer.getId());

    }
}
