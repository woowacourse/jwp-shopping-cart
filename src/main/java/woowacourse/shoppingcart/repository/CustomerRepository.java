package woowacourse.shoppingcart.repository;

import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.Entity.CartEntity;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.exception.custum.DuplicatedValueException;
import woowacourse.shoppingcart.exception.custum.InvalidLoginException;
import woowacourse.shoppingcart.exception.custum.InvalidPasswordException;
import woowacourse.shoppingcart.exception.custum.ResourceNotFoundException;
import woowacourse.shoppingcart.repository.dao.CartItemDao;
import woowacourse.shoppingcart.repository.dao.CustomerDao;

@Repository
public class CustomerRepository {

    private final CustomerDao customerDao;
    private final CartItemDao cartItemDao;

    public CustomerRepository(final CustomerDao customerDao,
                              CartItemDao cartItemDao) {
        this.customerDao = customerDao;
        this.cartItemDao = cartItemDao;
    }

    public Long create(final Customer customer) {
        return customerDao.create(customer);
    }

    public Customer findById(final Long id) {
        return customerDao.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public Customer findValidUser(final String username, final String password) {
        return customerDao.findByUsernameAndPassword(username, password)
                .orElseThrow(InvalidLoginException::new);
    }

    public void update(final Customer newCustomer) {
        if (customerDao.update(newCustomer) == 0) {
            throw new ResourceNotFoundException();
        }
    }

    public void updatePassword(final Long id, final Password oldPassword, final Password newPassword) {
        findById(id);
        if (customerDao.updatePassword(id, oldPassword.get(), newPassword.get()) == 0) {
            throw new InvalidPasswordException();
        }
    }

    public void delete(final Long id) {
        customerDao.findWithdrawalById(id)
                .ifPresent(customer -> {
                    throw new ResourceNotFoundException();
                });
        if (customerDao.delete(id) == 0) {
            throw new ResourceNotFoundException();
        }
        deleteCartItemById(id);
    }

    private void deleteCartItemById(Long id) {
        cartItemDao.findCartsByCustomerId(id)
                .stream()
                .map(CartEntity::getId)
                .forEach(cartItemDao::deleteById);
    }

    public void matchPassword(final Long id, final String password) {
        customerDao.findByUsernameAndPassword(findById(id).getUsername(), password)
                .orElseThrow(InvalidPasswordException::new);
    }

    public void validateDuplicateUsername(final String username) {
        customerDao.findByUserName(username)
                .ifPresent(customer -> {
                    throw new DuplicatedValueException();
                });
    }

    public void validateDuplicateNickname(final String nickname) {
        customerDao.findByNickname(nickname)
                .ifPresent(customer -> {
                    throw new DuplicatedValueException();
                });
    }
}
