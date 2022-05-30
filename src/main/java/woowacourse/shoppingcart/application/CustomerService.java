package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.exception.DuplicatedNameException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Transactional
    public Long signUp(CustomerRequest customerRequest) {
        validateDuplicateName(customerRequest.getUserName());
        return customerDao.save(customerRequest.getUserName(), customerRequest.getPassword());
    }

    private void validateDuplicateName(String name) {
        if (customerDao.existsByUserName(name)) {
            throw new DuplicatedNameException();
        }
    }

    @Transactional(readOnly = true)
    public CustomerResponse getMeById(final Long id) {
        final Customer customer = customerDao.findById(id)
                .orElseThrow(InvalidCustomerException::new);
        return new CustomerResponse(customer);
    }

    @Transactional
    public CustomerResponse updateById(final Long id, final CustomerRequest customerRequest) {
        final Customer customer = customerDao.findById(id)
                .orElseThrow(InvalidCustomerException::new);

        customer.validateUserNameChange(customerRequest.getUserName());

        final Customer updatedCustomer = customerDao.update(
                customer.getId(), customerRequest.getUserName(), customerRequest.getPassword());
        return new CustomerResponse(updatedCustomer);
    }
}
