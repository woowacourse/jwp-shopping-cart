package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.exception.DuplicatedNameException;
import woowacourse.shoppingcart.application.exception.InvalidCustomerException;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.domain.PasswordEncrypter;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.DuplicateResponse;

@Service
@Transactional
public class CustomerService {

    private final CustomerDao customerDao;
    private final PasswordEncrypter passwordEncrypter;

    public CustomerService(final CustomerDao customerDao, final PasswordEncrypter passwordEncrypter) {
        this.customerDao = customerDao;
        this.passwordEncrypter = passwordEncrypter;
    }

    public Long signUp(CustomerRequest customerRequest) {
        validateDuplicateName(customerRequest.getUserName());

        Password password = passwordEncrypter.encode(customerRequest.getPassword());
        Customer customer = new Customer(customerRequest.getUserName(), password);

        return customerDao.save(customer.getUserName(), customer.getPassword().getValue());
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

    public CustomerResponse updateById(final Long id, final CustomerRequest customerRequest) {
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

    @Transactional(readOnly = true)
    public DuplicateResponse isDuplicateUserName(final String userName) {
        return new DuplicateResponse(customerDao.existsByUserName(userName));
    }
}
