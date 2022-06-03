package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.CustomerCreationRequest;
import woowacourse.shoppingcart.dto.CustomerUpdationRequest;
import woowacourse.shoppingcart.exception.badrequest.DuplicateEmailException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(final CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Long create(final CustomerCreationRequest request) {
        final boolean existEmail = isExistEmail(request.getEmail());
        if (existEmail) {
            throw new DuplicateEmailException();
        }
        final Customer customer = request.toCustomer();
        return customerDao.save(customer);
    }

    @Transactional(readOnly = true)
    public boolean isExistEmail(final String email) {
        return customerDao.existEmail(email);
    }

    @Transactional(readOnly = true)
    public Customer getByEmail(final String email) {
        return customerDao.findByEmail(email);
    }

    public void update(final Customer customer, final CustomerUpdationRequest request) {
        final Customer updatedCustomer = new Customer(request.getNickname(), customer.getEmail(),
                request.getPassword());
        customerDao.updateById(customer.getId(), updatedCustomer);
    }

    public void delete(final Customer customer) {
        customerDao.deleteById(customer.getId());
    }
}
