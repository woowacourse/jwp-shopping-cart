package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.exception.DuplicateNameException;

@Service
@Transactional
public class CustomerService {
    private final CustomerDao customerDao;

    public CustomerService(final CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void addCustomer(final CustomerRequest customerRequest) {
        if (customerDao.existsByName(customerRequest.getUserName())) {
            throw new DuplicateNameException();
        }

        customerDao.save(Customer.of(customerRequest.getUserName(), customerRequest.getPassword()));
    }

    public void deleteCustomer(final String userName) {
        customerDao.deleteByName(userName);
    }

    @Transactional(readOnly = true)
    public CustomerResponse findCustomer(final String userName) {
        final Customer customer = customerDao.findCustomerByName(userName);
        return new CustomerResponse(customer.getName());
    }

    public void editCustomer(final String userName, final CustomerRequest editRequest) {
        customerDao.updateCustomer(Customer.of(userName, editRequest.getPassword()));
    }

    @Transactional(readOnly = true)
    public boolean existsCustomer(String userName) {
        return customerDao.existsByName(userName);
    }
}
