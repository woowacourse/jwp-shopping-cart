package woowacourse.shoppingcart.repository;

import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.exception.DuplicateNicknameException;
import woowacourse.shoppingcart.exception.DuplicateUsernameException;
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

    public Long insert(final Customer customer) {
        return customerDao.insert(customer);
    }

    public Customer selectById(final Long id) {
        return customerDao.selectById(id);
    }

    public Customer selectByUsernameAndPassword(final String username, final String password) {
        return customerDao.selectByUsernameAndPassword(username, password);
    }

    public void update(final Customer newCustomer) {
        customerDao.update(newCustomer);
    }

    public void updatePassword(final Long id, final Password oldPassword, final Password newPassword) {
        customerDao.selectById(id);
        customerDao.updatePassword(id, oldPassword.getPassword(), newPassword.getPassword());
    }

    public void delete(final Long id) {
        customerDao.delete(id);
        cartItemDao.deleteAllByCustomerId(id);
    }

    public void validateDuplicateUsername(final String username) {
        if (customerDao.existsUsername(username)) {
            throw new DuplicateUsernameException("이미 가입된 이메일입니다.");
        }
    }

    public void validateDuplicateNickname(final String nickname) {
        if (customerDao.existNickname(nickname)) {
            throw new DuplicateNicknameException("이미 존재하는 닉네임입니다.");
        }
    }

    public void matchPassword(final Long id, final String password) {
        if (!customerDao.existsPasswordOfId(id, password)) {
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
        }
    }
}
