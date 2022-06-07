package woowacourse.shoppingcart.repository;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.exception.InvalidPasswordException;
import woowacourse.shoppingcart.repository.dao.CartItemDao;
import woowacourse.shoppingcart.repository.dao.CustomerDao;

@Repository
public class CustomerRepository {

    private final CustomerDao customerDao;
    private final CartItemDao cartItemDao;

    public CustomerRepository(final CustomerDao customerDao, final CartItemDao cartItemDao) {
        this.customerDao = customerDao;
        this.cartItemDao = cartItemDao;
    }

    public Long create(final Customer customer) {
        return customerDao.create(customer);
    }

    public Customer findById(final Long id) {
        return customerDao.findById(id);
    }

    public Customer login(final String username, final String password) {
        return customerDao.login(username, password);
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
        cartItemDao.deleteByCustomerId(id);
    }

    public void validateDuplicateUsername(final String username) {
        if (customerDao.checkDuplicatedUsername(username)) {
            throw new DuplicateKeyException("중복된 값이 존재합니다.");
        }
    }

    public void validateDuplicateNickname(final String nickname) {
        if (customerDao.checkDuplicatedNickname(nickname)) {
            throw new DuplicateKeyException("중복된 값이 존재합니다.");
        }
    }

    public void matchPassword(final Long id, final String password) {
        if (!customerDao.matchPassword(id, password)) {
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
        }
    }
}
