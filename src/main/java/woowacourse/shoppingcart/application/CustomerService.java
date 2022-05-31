package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerCreationRequest;
import woowacourse.shoppingcart.dto.CustomerUpdationRequest;
import woowacourse.shoppingcart.exception.DuplicateEmailException;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(final CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Long create(final CustomerCreationRequest request) {
        final boolean existEmail = customerDao.existEmail(request.getEmail());
        if (existEmail) {
            throw new DuplicateEmailException();
        }
        final Customer customer = new Customer(request.getNickname(), request.getEmail(), request.getPassword());
        return customerDao.save(customer);
    }

    public Customer getByEmail(final String email) {
        return customerDao.findByEmail(email);
    }

    public void update(final Customer customer, final CustomerUpdationRequest request) {
        final Customer updatedCustomer = new Customer(request.getNickname(), customer.getEmail(), request.getPassword());
        customerDao.updateById(customer.getId(), updatedCustomer);
    }

    public void delete(final Customer customer) {
        customerDao.deleteById(customer.getId());
    }
}
