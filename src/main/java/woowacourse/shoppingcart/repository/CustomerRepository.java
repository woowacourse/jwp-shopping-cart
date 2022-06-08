package woowacourse.shoppingcart.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.domain.customer.password.Password;
import woowacourse.shoppingcart.domain.customer.password.PasswordFactory;
import woowacourse.shoppingcart.domain.customer.password.PasswordType;

@Repository
public class CustomerRepository {

    private final CustomerDao customerDao;

    public CustomerRepository(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Long createCustomer(final Customer customer) {
        return customerDao.createCustomer(customer);
    }

    public boolean checkEmailDuplication(final Email email) {
        try {
            customerDao.findEmail(email);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public Password findPasswordByEmail(final Email email) {
        return PasswordFactory.of(PasswordType.EXISTED, customerDao.findPasswordByEmail(email));
    }

    public Customer findByUserEmail(final Email email) {
        return customerDao.findByUserEmail(email);
    }

    public void updateCustomer(final Customer modifiedCustomer) {
        final int affectedRows = customerDao.updateCustomer(modifiedCustomer);
        if (affectedRows != 1) {
            throw new IllegalArgumentException("업데이트가 되지 않았습니다.");
        }
    }

    public void deleteCustomer(final Email email) {
        final int affectedRows = customerDao.deleteCustomer(email);
        if (affectedRows != 1) {
            throw new IllegalArgumentException("삭제가 되지 않았습니다.");
        }
    }
}
