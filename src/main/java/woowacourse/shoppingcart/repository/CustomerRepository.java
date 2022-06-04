package woowacourse.shoppingcart.repository;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.exception.InvalidLoginException;
import woowacourse.shoppingcart.exception.InvalidPasswordException;
import woowacourse.shoppingcart.repository.dao.CustomerDao;

@Repository
public class CustomerRepository {

    private static final String DUPLICATE_VALUE_ERROR_MESSAGE = "중복된 값이 존재합니다.";
    private static final String INVALID_PASSWORD_ERROR_MESSAGE = "비밀번호가 일치하지 않습니다.";
    private static final String INVALID_LOGIN_ERROR_MESSAGE = "로그인 할 수 없습니다.";

    private final CustomerDao customerDao;

    public CustomerRepository(final CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Long create(final Customer customer) {
        return customerDao.create(customer);
    }

    public Customer findById(final Long id) {
        return customerDao.findById(id);
    }

    public Customer findValidUser(final String username, final String password) {
        try {
            return customerDao.findByUsernameAndPassword(username, password);
        } catch (EmptyResultDataAccessException exception) {
            throw new InvalidLoginException(INVALID_LOGIN_ERROR_MESSAGE);
        }
    }

    public void update(final Customer newCustomer) {
        customerDao.update(newCustomer);
    }

    public void updatePassword(final Long id, final Password oldPassword, final Password newPassword) {
        customerDao.findById(id);
        customerDao.updatePassword(id, oldPassword.getPassword(), newPassword.getPassword());
    }

    public void delete(final Long id) {
        customerDao.delete(id);
    }

    public void validateDuplicateUsername(final String username) {
        if (customerDao.checkDuplicatedUsername(username)) {
            throw new DuplicateKeyException(DUPLICATE_VALUE_ERROR_MESSAGE);
        }
    }

    public void validateDuplicateNickname(final String nickname) {
        if (customerDao.checkDuplicatedNickname(nickname)) {
            throw new DuplicateKeyException(DUPLICATE_VALUE_ERROR_MESSAGE);
        }
    }

    public void matchPassword(final Long id, final String password) {
        if (!customerDao.matchPassword(id, password)) {
            throw new InvalidPasswordException(INVALID_PASSWORD_ERROR_MESSAGE);
        }
    }
}
