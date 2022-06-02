package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerRequest.UserNameAndPassword;
import woowacourse.shoppingcart.dto.CustomerRequest.UserNameOnly;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.DuplicateResponse;
import woowacourse.shoppingcart.exception.DuplicatedNameException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
@Transactional
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Long signUp(UserNameAndPassword customerRequest) {
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

    public CustomerResponse updateById(final Long id, final CustomerRequest.UserNameAndPassword customerRequest) {
        final Customer customer = customerDao.findById(id)
                .orElseThrow(InvalidCustomerException::new);

        customer.validateUserNameChange(customerRequest.getUserName());

        final Customer updatedCustomer = customerDao.update(
                customer.getId(), customerRequest.getUserName(), customerRequest.getPassword());
        return new CustomerResponse(updatedCustomer);
    }

    public void deleteById(final Long id) {
        final Customer customer = customerDao.findById(id)
                .orElseThrow(InvalidCustomerException::new);

        customerDao.deleteById(customer.getId());
    }

    public DuplicateResponse isDuplicateUserName(final UserNameOnly customerRequest) {
        final boolean isDuplicated = customerDao.existsByUserName(customerRequest.getUserName());
        return new DuplicateResponse(isDuplicated);
    }
}
